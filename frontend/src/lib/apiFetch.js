export function apiFetch(path, options) {
  const baseUrl = import.meta.env.VITE_BACKEND_API_BASE_URL;
  return fetch(`${baseUrl}${path}`, options);
}