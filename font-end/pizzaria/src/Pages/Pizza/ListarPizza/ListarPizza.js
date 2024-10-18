import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ListarPizza.css';

function ListarPizzas() {
  const [pizzas, setPizzas] = useState([]);
  const [erro, setErro] = useState(null);

  useEffect(() => {
    const fetchPizzas = async () => {
      try {
        const response = await axios.get(apiURL + '/sabores/listar');
        setPizzas(response.data);
      } catch (error) {
        setErro('Erro ao buscar sabores: ' + error.message);
      }
    };

    fetchPizzas();
  }, []);

  return (
    <div className="background-container">
      <div className="container">
        <h2 className="pizza-title">Listagem de Sabores</h2>
        {erro && <p className="error-message">{erro}</p>}
        <div className="grid">
          {pizzas.map((pizza) => (
            <div key={pizza.idsabor} className="grid-item">
              <div className="pizza-image-container">
                {pizza.imagem && <img src={pizza.imagem} alt={pizza.sabor} className="pizza-image" />}
              </div>
              <h3 className="pizza-subtitle">{pizza.sabor}</h3>
              <p className="pizza-description">{pizza.descricao}</p>
              <p className="pizza-valor">Valor: R$ {pizza.valor.toFixed(2)}</p>
              <p className="pizza-tamanho">Tamanho: {pizza.tamanho}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default ListarPizzas;
