const createURL = (url, values) => {
  return url + "?" + new URLSearchParams(values).toString();
};

const request = (method, url, values, body, type) => {
  let xml = new XMLHttpRequest();
  xml.open(method, url);
  xml.send(JSON.stringify(body));
  xml.setRequestHeader("Content-Type", type);
  xml.onload = (res) => {
    console.log(JSON.parse(xml.responseText));
  };
};
