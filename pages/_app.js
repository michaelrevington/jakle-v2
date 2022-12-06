import { Component as ReactComponent } from "react";
import "../styles/globals.css";
import "../styles/const.css";
import { useRouter } from "next/router";
import PageLoader from "../src/components/PageLoader";
import Head from "next/head";

const Application = (props) => {
  return <ApplicationComponent {...props} router={useRouter()} />;
};

class ApplicationComponent extends ReactComponent {
  constructor(props) {
    super(props);

    this.state = {
      progress: 0,
      transition: 0.75,
    };

    this.load = this.load.bind(this);
    this.complete = this.complete.bind(this);
  }

  load() {
    this.setState({ progress: 30, transition: 0.75 });
    setTimeout(() => {
      this.setState({ progress: 60 });
    }, 750);
  }

  complete() {
    this.setState({ progress: 100 });
    setTimeout(() => {
      this.setState({ progress: 0, transition: 0 });
    }, 751);
  }

  componentDidMount() {
    const router = this.props.router;

    router.events.on("routeChangeStart", this.load);
    router.events.on("routeChangeComplete", this.complete);
    router.events.on("routeChangeError", this.complete);
  }

  render() {
    const { Component, pageProps } = this.props,
      { progress, transition } = this.state;

    return (
      <>
        <Head>
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <link href={"/static/favicon.ico"} rel="icon"/>
        </Head>

        <PageLoader
          color="#00ccff"
          progress={progress}
          transition={transition}
        />
        <Component {...pageProps} />
      </>
    );
  }
}

export default Application;
