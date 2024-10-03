import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './PedidoConcluido.css';

const PedidoConcluido = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { total } = location.state || {};
  const deliveryTime = '40 minutos';

  return (
    <div className="pedido-concluido-container">
      <h2 className='pedido-caminho'>Seu pedido está a caminho!</h2>
      <p className='text-pedido-sucess'>Obrigado pelo pedido. Atualizaremos o status do seu pedido assim que o restaurante confirmar.</p>
      <div className="pedido-concluido-status">
        <div className="pedido-concluido-icon">&#10004;</div>
        <p className='text-pedido-sucess'>Valor pago: {total ? `R$ ${total.toFixed(2)}` : 'N/A'}</p>
        <p className='text-pedido-sucess'>Tempo estimado de entrega: {deliveryTime}</p>
      </div>
    </div>
  );
};

export default PedidoConcluido;
