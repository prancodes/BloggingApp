// ========================================================
// BlogSpace Service Worker (PWA)
// ========================================================

const CACHE_NAME = 'blogspace-v1';

// List of files to cache immediately when the app installs
const ASSETS = [
  // 1. App Shell (HTML & Core Logic)
  '/',
  '/validation.js',

  // 2. Styles & Scripts (Bootstrap)
  '/webjars/bootstrap/5.3.8/css/bootstrap.min.css',
  '/webjars/bootstrap/5.3.8/js/bootstrap.bundle.min.js',

  // 3. PWA Icons & Manifest (The files in your 'favicons' folder)
  '/favicons/favicon.svg',
  '/favicons/favicon.ico',
  '/favicons/favicon-96x96.png',
  '/favicons/apple-touch-icon.png',
  '/favicons/site.webmanifest',
  
  // CRITICAL: These two are required for the "Add to Home Screen" icon on mobile
  '/favicons/web-app-manifest-192x192.png',
  '/favicons/web-app-manifest-512x512.png'
];

// 1. INSTALL EVENT
// Runs once when the user first visits (or updates) the app.
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      // console.log('[Service Worker] Caching static assets...');
      return cache.addAll(ASSETS);
    })
  );
});

// 2. FETCH EVENT (Strategy: Network First -> Cache -> Dynamic Cache)
self.addEventListener('fetch', (event) => {
  // Skip cross-origin requests, POST requests, and Chrome Extensions
  if (event.request.method !== 'GET') return;
  if (!event.request.url.startsWith('http')) return; // Skip non-HTTP requests

  event.respondWith(
    fetch(event.request)
      .then((networkResponse) => {
        // Step A: If network response is successful (Online)
        if (networkResponse && networkResponse.status === 200 && networkResponse.type === 'basic') {
          // Clone the response and store it in the cache for future offline use
          const responseToCache = networkResponse.clone();
          caches.open(CACHE_NAME).then((cache) => {
            cache.put(event.request, responseToCache);
          });
        }
        return networkResponse;
      })
      .catch(() => {
        // Step B: If network fails (Offline), try to serve from cache
        // console.log('[Service Worker] Offline! Fetching from cache: ' + event.request.url);
        return caches.match(event.request)
          .then((cachedResponse) => {
            if (cachedResponse) {
              return cachedResponse;
            }
            // Step C: If page is not in cache (User never visited it)
            // Fallback to Home page so the app doesn't look broken
            if (event.request.mode === 'navigate') {
              return caches.match('/');
            }
          });
      })
  );
});

// 3. ACTIVATE EVENT (Cleanup old caches)
self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((keyList) => {
      return Promise.all(keyList.map((key) => {
        if (key !== CACHE_NAME) {
          return caches.delete(key);
        }
      }));
    })
  );
});