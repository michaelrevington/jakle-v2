import Head from "next/head";
import { Component } from "react";
import { AltonaSans } from "../../src/font";
import { FadeIn } from "../../src/tag";
import ComponentStyles from '../../styles/Component.module.css'

export default class Index extends Component {
    render() {
        return (
            <>
                <Head>
                    <title>Jakle - Home</title>
                </Head>

                <main className={AltonaSans.className}>

                </main>
            </>
        );
    }
}
