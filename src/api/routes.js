const API = "/api/v2";

const SOCIAL = "/social";
const ACCOUNT = "/account";

export const API_ROUTES = {
  LOGIN: API + "/login",
  SIGNUP: API + "/signup",
  LOGOUT: API + ACCOUNT + "/logout",
  UPDATE: API + ACCOUNT + "/update",
  UPDATE_ACCESS: API + ACCOUNT + "/update/access",
};
