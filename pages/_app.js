import { Component as ReactComponent } from "react";
import "../styles/globals.css";
import "../styles/const.css";

class Application extends ReactComponent {
  render() {
    const { Component, pageProps } = this.props;

    return <Component {...pageProps} />;
  }
}

export default Application;
