import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './PedidoList.css';

const PedidoList = () => {
    const [pedidos, setPedidos] = useState([]);
    const [erro, setErro] = useState(null);
    const [totalPedidos, setTotalPedidos] = useState(0);

    useEffect(() => {
        fetchPedidos();
    }, []);

    useEffect(() => {
        // Calcula o total de pedidos
        const total = pedidos.reduce((acc, pedido) => acc + pedido.total, 0);
        setTotalPedidos(total);
    }, [pedidos]);

    const formatarValorBRL = (valor) => {
        return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
    };

    const fetchPedidos = async () => {
        try {
            const response = await axios.get('http://localhost:8080/carrinho/listar');
            setPedidos(response.data);
        } catch (error) {
            console.error("Erro ao buscar pedidos: ", error);
            setErro("Erro ao buscar pedidos. Verifique o console para mais informações.");
        }
    };

    return (
        <div>
            <h1>Lista de Pedidos</h1>
            {erro && <p className="error-message">{erro}</p>}
            <table>
                <thead>
                    <tr>
                        <th>ID Pedido</th>
                        <th>ID Cliente</th>
                        <th>Nome Cliente</th>
                        <th>CPF</th>
                        <th>CEP</th>
                        <th>Endereço</th>
                        <th>Número</th>
                        <th>Data Pedido</th>
                        <th>Status</th>
                        <th>Total</th>
                        <th>Itens do Pedido</th>
                    </tr>
                </thead>
                <tbody>
                    {pedidos.map(pedido => (
                        <tr key={pedido.idPedido}>
                            <td>{pedido.idPedido}</td>
                            <td>{pedido.idCliente}</td>
                            <td>{pedido.account ? pedido.account.nome : '-'}</td>
                            <td>{pedido.account ? pedido.account.cpf : '-'}</td>
                            <td>{pedido.account ? pedido.account.cep : '-'}</td>
                            <td>{pedido.account ? pedido.account.endereco : '-'}</td>
                            <td>{pedido.account ? pedido.account.numero : '-'}</td>
                            <td>{new Date(pedido.dataPedido).toLocaleString()}</td>
                            <td>{pedido.status}</td>
                            <td>{formatarValorBRL(pedido.total)}</td>
                            <td>
                                <ul>
                                    {pedido.itensPedido.map((item, index) => (
                                        <li key={index}>
                                            <strong>Item ID:</strong> {item.idItem}, 
                                            <strong> Tipo:</strong> {item.tipo}, 
                                            <strong> Sabor:</strong> {item.sabor.sabor}
                                        </li>
                                    ))}
                                </ul>
                            </td>
                        </tr>
                    ))}
                </tbody>
                <tfoot>
                    <tr>
                        <td colSpan="11" className="footer-total">Valor Total: <strong>{formatarValorBRL(totalPedidos)}</strong></td>
                    </tr>
                </tfoot>
            </table>
        </div>
    );
};

export default PedidoList;
