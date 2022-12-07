const API = "/api/v2";

const ACCOUNT = "/account";
const SOCIAL = "/social";

export const API_ROUTES = {
  LOGIN: API + "/login",
  SIGNUP: API + "/signup",
  LOGOUT: API + ACCOUNT + "/logout",
  UPDATE: API + ACCOUNT + "/update",
  UPDATE_ACCESS: API_ROUTES.UPDATE + "/access",
};
