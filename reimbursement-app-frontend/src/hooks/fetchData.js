export async function fetchDataFromServer(url) {
  const response = await fetch(url);

  if (!response.ok) {
    throw new Error('Network response was not ok');
  }

  const responseData = await response.text();
  return responseData;
}
