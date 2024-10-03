import React, { useEffect } from "react";
import "./App.css";
import Footer from "./Components/Footer/Footer";
import Menu from "./Components/Menu/Menu";
import Principal from "./Components/Principal/Principal";
import Sobre from "./Components/Sobre/Sobre";
import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import LayoutCriarConta from "./Layout/LayoutCriarConta";
import LayoutLogin from "./Layout/LayoutLogin";
import LayoutCriarPizza from "./Layout/LayoutCriarPizza";
import LayoutListarPizza from "./Layout/LayoutListarPizza";
import LayoutMontarPizza from "./Layout/LayoutMontarPizza";
import Carrinho from "./Layout/LayoutCarrinho";
import PedidoConcluido from "./Pages/Carrinho/PedidoConcluido/PedidoConcluido";
import LayoutAtualizarInformacoes from "./Layout/LayoutAtualizarInformacoes"
import { scroller } from "react-scroll";
import PedidosList from "./Layout/LayoutPedidosList";

const ScrollToSection = () => {
  const location = useLocation();

  useEffect(() => {
    const hash = location.hash.replace("#", "");
    if (hash) {
      scroller.scrollTo(hash, {
        smooth: true,
        duration: 500,
        offset: -70,
      });
    }
  }, [location]);

  return null;
};

function App() {
  return (
    <div className="App">
      <Router>
        <Menu />
        <ScrollToSection />
        <div className="App-content">
          <Routes>
            <Route
              path="/"
              element={
                <div>
                  <Principal />
                  <div id="sobre">
                    <Sobre />
                  </div>         
                  <div id="contato">
                    <Footer />
                  </div>
                </div>
              }
            />
            <Route
              path="/home"
              element={
                <div>
                  <Principal />
                  <div id="sobre">
                    <Sobre />
                  </div>
                  <div id="contato">
                    <Footer />
                  </div>
                </div>
              }
            />
             <Route
              path="/sabores"
              element={
                <div id="sabores">
                  <LayoutListarPizza />
                </div>
              }
            />
            <Route path="/criar-conta" element={<LayoutCriarConta />} />
            <Route path="/login" element={<LayoutLogin />} />
            <Route path="/criar-pizza" element={<LayoutCriarPizza />} />
            <Route path="/listar-pizza" element={<LayoutListarPizza />} />
            <Route path="/montar-pizza" element={<LayoutMontarPizza />} />
            <Route path="/carrinho" element={<Carrinho />} />
            <Route path="/pedidoConcluido" element={<PedidoConcluido />} />
            <Route path="/atualizarInformacoes" element={<LayoutAtualizarInformacoes />} />
            <Route path="/listarPedidos" element={<PedidosList />} />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;
