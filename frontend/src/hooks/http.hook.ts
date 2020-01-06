import { useState, useCallback } from 'react';

export const useHttp = () => {

  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const request = useCallback(async (
    url: string,
    method = 'GET',
    body = null,
    headers = {}) => {
    try {
      setLoading(true);
      const responce = await fetch(url, { method, body, headers });
      const data = await responce.json();

      if (!responce.ok) {
        setError(data.message);
        throw new Error(data.message || "Something went wrong");
      }
      setLoading(false);
      return data;

    } catch (e) {
      setLoading(false);
      setError(e.message)
      throw e;
    }
  }, [])

  const clearError = () => setError(null);

  return { loading, request, error, clearError }
}

export default useHttp;