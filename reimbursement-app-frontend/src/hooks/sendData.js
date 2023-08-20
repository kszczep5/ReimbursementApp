export async function sendDataToServer(url, payload) {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const responseData = await response.text();
  return responseData;
}
