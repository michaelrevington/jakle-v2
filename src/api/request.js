import { API_ROUTES } from "./routes";

const createURL = (url, params) => {
  return url + "?" + new URLSearchParams(params).toString();
};

const request = (url, params, method, body, headers, callback) => {
  return new Promise(() => {
    let xml = new XMLHttpRequest();
    xml.open(method, createURL(url, params));
    Object.entries(headers).forEach((entry) => {
      xml.setRequestHeader(entry[0], entry[1]);
    });
    xml.send(JSON.stringify(body));
    xml.onload = (e) => {
      callback(xml);
    };
  });
};

const login = (params, body, callback) => {
  return request(
    API_ROUTES.LOGIN,
    params,
    "post",
    body,
    {
      "Content-Type": "application/json",
    },
    callback
  );
};

const signup = (body, callback) => {
  return request(
    API_ROUTES.SIGNUP,
    null,
    "post",
    body,
    {
      "Content-Type": "application/json",
    },
    callback
  );
};

const logout = (callback) => {
  return request(
    API_ROUTES.LOGOUT,
    null,
    "delete",
    null,
    {
      "Content-Type": "application/json",
    },
    callback
  );
};

const update = (body, callback) => {
  return request(
    API_ROUTES.UPDATE,
    null,
    "post",
    body,
    {
      "Content-Type": "application/json",
    },
    callback
  );
};

const update_access = (body, callback) => {
  return request(
    API_ROUTES.UPDATE_ACCESS,
    null,
    "post",
    body,
    {
      "Content-Type": "application/json",
    },
    callback
  );
};

export { login, logout, signup, update, update_access };
