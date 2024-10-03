import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { MdExitToApp } from "react-icons/md";
import "./AtualizarInformacoes.css";
import CustomInput from "../../Util/CustomInput";
import CustomButton from "../../Util/CustomButton";
import { handleCepChange } from "../../Util/CepUtil";

function EditarDadosUsuario() {
    const { id } = useParams();
    const [usuario, setUsuario] = useState(null);
    const [cep, setCep] = useState('');
    const [endereco, setEndereco] = useState('');
    const [numero, setNumero] = useState('');
    const navigate = useNavigate();

    const userId = localStorage.getItem('userId');

    useEffect(() => {
        const fetchUsuario = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/usuarios/${userId}`);
                setUsuario(response.data);
                setCep(response.data.cep);
                setEndereco(response.data.endereco);
                setNumero(response.data.numero);
            } catch (error) {
                console.error('Erro ao buscar usuário:', error);
            }
        };

        if (userId) {
            fetchUsuario();
        }
    }, [userId]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const trimmedEndereco = endereco.trim();
        const trimmedNumero = numero.trim();

        if (trimmedEndereco === "" || trimmedNumero === "") {
            alert("Por favor, preencha todos os campos corretamente.");
            return;
        }

        try {
            const response = await axios.put(`http://localhost:8080/usuarios/${userId}/editar`, {
                ...usuario,
                cep,
                endereco: trimmedEndereco,
                numero: trimmedNumero,
            });
            alert('Dados atualizados com sucesso!');
        } catch (error) {
            console.error('Erro ao atualizar dados do usuário:', error);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('userId');
        navigate("/login");
    };

    if (!usuario) {
        return <p>Carregando dados do usuário...</p>;
    }

    return (
        <div className="editar-dados-usuario-container">
            <div className="logout-button" onClick={handleLogout}>
                <MdExitToApp size={24} />
            </div>
            <h2>Editar Dados do Usuário</h2>
            <form className="editar-dados-usuario-form" onSubmit={handleSubmit}>
                <CustomInput
                    type="text"
                    placeholder="CPF"
                    value={usuario.cpf}
                    readOnly
                    disabled
                    styleType="disabled"
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="Nome"
                    value={usuario.nome}
                    readOnly
                    disabled
                    styleType="disabled"
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="CEP"
                    value={cep}
                    onChange={(e) => handleCepChange(e, setCep, setEndereco)}
                    maxLength={9}
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="Endereço"
                    value={endereco}
                    onChange={(e) => setEndereco(e.target.value)}
                    required
                    readOnly={!cep}
                />
                <CustomInput
                    type="text"
                    placeholder="Número"
                    value={numero}
                    onChange={(e) => setNumero(e.target.value)}
                    required
                />
                <CustomButton type="submit" text="Salvar Alterações" className="custom-button" />
            </form>
        </div>
    );
}

export default EditarDadosUsuario;