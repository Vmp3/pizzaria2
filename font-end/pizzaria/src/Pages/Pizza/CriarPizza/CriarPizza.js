import React, { useState } from "react";
import axios from "axios";
import CustomInput from "../../../Util/CustomInput";
import CustomButton from "../../../Util/CustomButton";
import "./CriarPizza.css";

function PizzaForm() {
  const [pizza, setPizza] = useState({
    imagem: "",
    sabor: "",
    descricao: "",
    valor: "",
    tamanho: ""
  });
  const [mensagem, setMensagem] = useState("");
  const [isEditing, setIsEditing] = useState(false);
  const [pizzaId, setPizzaId] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;
    setPizza((prevPizza) => ({
      ...prevPizza,
      [name]: value
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = isEditing
        ? await axios.put(`http://localhost:8080/sabores/atualizar/${pizzaId}`, pizza)
        : await axios.post("http://localhost:8080/sabores/adicionar", pizza);

      setMensagem(isEditing ? "Sabor de pizza atualizado com sucesso!" : "Sabor de pizza adicionado com sucesso!");
      setPizza({
        imagem: "",
        sabor: "",
        descricao: "",
        valor: "",
        tamanho: ""
      });
      setPizzaId("");
      setIsEditing(false);
    } catch (error) {
      console.error("Erro ao gerenciar sabor de pizza:", error);
      if (error.response) {
        setMensagem("Erro ao gerenciar sabor de pizza: " + error.response.data);
      } else {
        setMensagem("Erro ao gerenciar sabor de pizza: " + error.message);
      }
    }
  };

  const handleFetchPizza = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/sabores/${pizzaId}`);
      const pizzaData = response.data;
      setPizza({
        imagem: pizzaData.imagem,
        sabor: pizzaData.sabor,
        descricao: pizzaData.descricao,
        valor: pizzaData.valor,
        tamanho: pizzaData.tamanho
      });
    } catch (error) {
      console.error("Erro ao buscar sabor de pizza:", error);
      setMensagem("Erro ao buscar sabor de pizza: " + error.message);
    }
  };

  const toggleEditMode = () => {
    setIsEditing((prev) => !prev);
    setMensagem("");
    setPizzaId("");
    setPizza({
      imagem: "",
      sabor: "",
      descricao: "",
      valor: "",
      tamanho: ""
    });
  };

  return (
    <div className="pizza-form-container">
      <h2>{isEditing ? "Editar Sabor de Pizza" : "Adicionar Sabores"}</h2>
      <div className="edit-button-container">
        <CustomButton text={isEditing ? "Cancelar Edição" : "Editar uma Pizza"} onClick={toggleEditMode} />
      </div>
      {isEditing && (
        <div className="edit-form-container">
          <CustomInput
            type="text"
            name="pizzaId"
            placeholder="ID da Pizza"
            value={pizzaId}
            onChange={(e) => setPizzaId(e.target.value)}
            required
          />
          <CustomButton text="Buscar Pizza" onClick={handleFetchPizza} styleType="button-search"/>
        </div>
      )}
      <form onSubmit={handleSubmit} className="pizza-form">
        <CustomInput
          type="text"
          name="imagem"
          placeholder="Imagem URL"
          value={pizza.imagem}
          onChange={handleChange}
          required
        />
        <CustomInput
          type="text"
          name="sabor"
          placeholder="Sabor"
          value={pizza.sabor}
          onChange={handleChange}
          required
        />
        <CustomInput
          type="text"
          name="descricao"
          placeholder="Descrição"
          value={pizza.descricao}
          onChange={handleChange}
          required
        />
        <CustomInput
          type="text"
          name="valor"
          placeholder="Valor"
          value={pizza.valor}
          onChange={handleChange}
          required
        />
        <select
          name="tamanho"
          value={pizza.tamanho}
          onChange={handleChange}
          required
        >
          <option value="">Selecione o tamanho</option>
          <option value="Pequena">Pequena</option>
          <option value="Média">Média</option>
          <option value="Grande">Grande</option>
        </select>
        <CustomButton text={isEditing ? "Atualizar Sabor" : "Adicionar Sabor"} />
      </form>
      <p className="mensagem">{mensagem}</p>
    </div>
  );
}

export default PizzaForm;
