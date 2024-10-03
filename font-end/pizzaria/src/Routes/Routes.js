import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import CriarConta from './Pages/criar-conta';
import Login from './Pages/Login/Login';
import Menu from "../Components/Menu/Menu";
import LayoutLogin from './Layout/LayoutLogin';
import Layout from './Layout/Layout';
import LayoutCriarPizza from './Layout/LayoutCriarPizza';
import LayoutListarPizza from './Layout/LayoutListarPizza';
import LayoutMontarPizza from "../Layout/LayoutMontarPizza";
import LayoutConcluido from "../Layout/LayoutPedidoConcluido";
import AtualizarInformacoes from "../Layout/LayoutAtualizarInformacoes"
import PedidosList from "../Layout/LayoutPedidosList";

function Routes() {
  return (
    <Router>
      <Switch>
        <Route path="/login">
          <LayoutLogin>
            <Login />
          </LayoutLogin>
        </Route>
        <Route path="/criar-conta">
          <Layout>
            <CriarConta />
          </Layout>
        </Route>
        <Route path="/listar-pizza">
          <Layout>
            <LayoutListarPizza />
          </Layout>
        </Route>
        <Route path="/criar-pizza">
          <LayoutCriarPizza />
        </Route>
        <Route path="/">
          <Layout>
            <Menu />
          </Layout>
        </Route>
        <Route path="/montar-pizza">
          <Layout>
            <LayoutMontarPizza />
          </Layout>
        </Route>
        <Route path="/pedidoConcluido">
          <Layout>
            <LayoutConcluido />
          </Layout>
        </Route>
        <Route path="/atualizarInformacoes">
          <Layout>
            <AtualizarInformacoes />
          </Layout>
        </Route>
        <Route path="/listarPedidos">
          <Layout>
            <PedidosList />
          </Layout>
        </Route>
      </Switch>
    </Router>
  );
}

export default Routes;
