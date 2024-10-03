import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Carrinho.css';
import CustomButton from "../../Util/CustomButton";

const Carrinho = () => {
  const [carrinho, setCarrinho] = useState([]);
  const [erro, setErro] = useState(null);
  const [userId, setUserId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const userIdFromStorage = localStorage.getItem('userId');
    setUserId(userIdFromStorage);

    const carrinhoAtual = JSON.parse(localStorage.getItem('carrinho')) || [];
    setCarrinho(carrinhoAtual);
  }, []);

  const handleRemoverPizza = (index) => {
    const novoCarrinho = [...carrinho];
    novoCarrinho.splice(index, 1);
    localStorage.setItem('carrinho', JSON.stringify(novoCarrinho));
    setCarrinho(novoCarrinho);
  };

  const finalizarPedido = async () => {
    if (carrinho.length === 0) {
      setErro('Não há itens no carrinho para finalizar o pedido.');
      return;
    }

    if (!userId) {
      setErro('Para finalizar o pedido, faça seu login.');
      return;
    }

    try {
      const itensPedido = [];

      carrinho.forEach(item => {
        item.sabores.forEach(sabor => {
          itensPedido.push({
            idPedido: 1,
            sabor: {
              idsabor: sabor.id
            },
            tipo: sabor.tipo
          });
        });
      });

      // Converte a data do pedido para GMT-3
      const datePedido = new Date();
      const offsetGMTMinus3 = -3 * 60 * 60 * 1000; // GMT-3 offset in milliseconds
      const dateGMTMinus3 = new Date(datePedido.getTime() + offsetGMTMinus3);

      const pedido = {
        idCliente: userId,
        dataPedido: dateGMTMinus3.toISOString(),
        status: 'PENDENTE',
        total: calcularTotal(),
        itensPedido: itensPedido
      };

      const response = await axios.post('http://localhost:8080/carrinho/adicionar', pedido);

      if (response.status === 200) {
        const transactionId = response.data.transactionId;
        const deliveryTime = response.data.deliveryTime;

        localStorage.removeItem('carrinho');
        setCarrinho([]);

        navigate('/pedidoConcluido', { state: { total: calcularTotal(), transactionId, deliveryTime } });
      } else {
        console.error('Erro ao finalizar pedido:', response);
        setErro('Erro ao finalizar pedido: A resposta do servidor não foi bem sucedida.');
      }
    } catch (error) {
      console.error('Erro ao finalizar pedido:', error);
      setErro('Erro ao finalizar pedido: ' + error.message);
    }
  };

  const calcularTotal = () => {
    return carrinho.reduce((total, item) => total + item.valor, 0);
  };

  return (
    <div className="carrinho-container">
      <h2>Carrinho de Compras</h2>
      {erro && <p className="error-message">{erro}</p>}
      {carrinho === null || carrinho.length === 0 ? (
        <p>O carrinho está vazio.</p>
      ) : (
        <div className="carrinho-items">
          {carrinho.map((item, index) => (
            <div key={index} className="carrinho-item">
              <h3>Pizza {item.tamanho}</h3>
              {item.sabores.map((sabor, saborIndex) => (
                <p key={saborIndex}>Sabor {saborIndex + 1}: {sabor.sabor} ({sabor.tipo})</p>
              ))}
              <p>Valor: {typeof item.valor === 'number' ? `R$ ${item.valor.toFixed(2)}` : 'Valor indisponível'}</p>
              <CustomButton
                onClick={() => handleRemoverPizza(index)}
                text="Remover"
                styleType="remove"
              />
            </div>
          ))}
        </div>
      )}
      <p>Total: R$ {calcularTotal().toFixed(2)}</p>
      <CustomButton onClick={finalizarPedido} disabled={carrinho === null || carrinho.length === 0} text="Finalizar Pedido"></CustomButton>
    </div>
  );
};

export default Carrinho;
