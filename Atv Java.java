<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Funcionário e Cálculo de Salário Líquido</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f7f9fb;
        }
        .container {
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.1);
        }
        .input-field {
            padding: 10px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            width: 100%;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        .input-field:focus {
            border-color: #3b82f6;
            outline: none;
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.5);
        }
    </style>
</head>
<body class="p-4 sm:p-8">

    <div class="container max-w-4xl mx-auto bg-white p-6 md:p-10 rounded-xl">
        <h1 class="text-3xl font-extrabold text-gray-900 mb-2">
            🧾 Cadastro e Cálculo de Salário
        </h1>
        <p class="text-gray-600 mb-8">
            Preencha os dados do funcionário para calcular automaticamente o salário líquido com descontos.
        </p>

        <div class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label for="nome" class="block text-sm font-medium text-gray-700 mb-1">Nome:</label>
                    <input type="text" id="nome" class="input-field" placeholder="Nome completo">
                </div>
                <div>
                    <label for="endereco" class="block text-sm font-medium text-gray-700 mb-1">Endereço:</label>
                    <input type="text" id="endereco" class="input-field" placeholder="Rua, número e bairro">
                </div>
                <div>
                    <label for="cidade" class="block text-sm font-medium text-gray-700 mb-1">Cidade:</label>
                    <input type="text" id="cidade" class="input-field" placeholder="Sua cidade">
                </div>
                <div>
                    <label for="estado" class="block text-sm font-medium text-gray-700 mb-1">Estado:</label>
                    <input type="text" id="estado" class="input-field" placeholder="Ex: SP, RJ">
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div>
                    <label for="idade" class="block text-sm font-medium text-gray-700 mb-1">Idade:</label>
                    <input type="number" id="idade" class="input-field" min="16" max="100" placeholder="Idade" value="30">
                </div>
                <div>
                    <label for="sexo" class="block text-sm font-medium text-gray-700 mb-1">Sexo:</label>
                    <select id="sexo" class="input-field appearance-none">
                        <option value="masculino">Masculino</option>
                        <option value="feminino">Feminino</option>
                        <option value="outro">Outro</option>
                    </select>
                </div>
                <div>
                    <label for="salario_bruto" class="block text-sm font-medium text-gray-700 mb-1">Salário Bruto (R$):</label>
                    <input type="number" id="salario_bruto" class="input-field" min="0" step="0.01" placeholder="Ex: 5000.00" value="5000.00">
                </div>
            </div>

            <div class="pt-4">
                <button 
                    id="btn-calcular"
                    onclick="calcularSalarioLiquido()" 
                    class="w-full md:w-auto px-6 py-3 bg-blue-600 text-white font-semibold rounded-lg shadow-lg hover:bg-blue-700 transition duration-300 transform hover:scale-[1.02] focus:outline-none focus:ring-4 focus:ring-blue-500 focus:ring-opacity-50"
                >
                    Calcular Salário Líquido
                </button>
            </div>
        </div>

        <div id="resultado" class="mt-8 p-6 bg-gray-50 border border-gray-200 rounded-lg hidden">
            <h2 class="text-xl font-bold text-gray-800 mb-4">
                📉 Resultado do Cálculo
            </h2>
            <p id="res_nome" class="text-lg mb-2">Nome: </p>
            <p id="res_bruto" class="text-lg mb-2">Salário Bruto: </p>
            <p id="res_descontos" class="text-lg text-red-600 mb-2 font-medium">Valor Total dos Descontos: </p>
            <p id="res_liquido" class="text-xl text-green-600 font-extrabold pt-2 border-t border-gray-300">Salário Líquido Final: </p>
        </div>
    </div>

    <script>
        
        function formatarMoeda(valor) {
            return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        }

        
        function calcularSalarioLiquido() {
            const nome = document.getElementById('nome').value;
            const endereco = document.getElementById('endereco').value;
            const sexo = document.getElementById('sexo').value;
            const cidade = document.getElementById('cidade').value;
            const estado = document.getElementById('estado').value;
            const idade = document.getElementById('idade').value;
            
            const salarioBrutoInput = document.getElementById('salario_bruto').value;
            const salarioBruto = parseFloat(salarioBrutoInput);

            if (isNaN(salarioBruto) || salarioBruto <= 0) {
                alert("Por favor, insira um Salário Bruto válido (número positivo).");
                return; 
            }

            const DESCONTO_VALE_TRANSPORTE = 0.02;
            const DESCONTO_VALE_ALIMENTACAO = 0.05;
            const DESCONTO_PLANO_SAUDE = 0.10; 
            
            const valorVT = salarioBruto * DESCONTO_VALE_TRANSPORTE;
            const valorVA = salarioBruto * DESCONTO_VALE_ALIMENTACAO;
            const valorPS = salarioBruto * DESCONTO_PLANO_SAUDE;

            const totalDescontos = valorVT + valorVA + valorPS;

            const salarioLiquido = salarioBruto - totalDescontos;


            document.getElementById('resultado').classList.remove('hidden');

            document.getElementById('res_nome').innerHTML = `Nome do Funcionário: <span class="font-semibold">${nome || 'Não Informado'}</span>`;

            document.getElementById('res_bruto').innerHTML = `Salário Bruto: <span class="font-semibold">${formatarMoeda(salarioBruto)}</span>`;

            document.getElementById('res_descontos').innerHTML = `Valor Total dos Descontos (17%): <span class="font-semibold">${formatarMoeda(totalDescontos)}</span>`;

            document.getElementById('res_liquido').innerHTML = `Salário Líquido Final: <span class="font-extrabold">${formatarMoeda(salarioLiquido)}</span>`;

            console.log("--- Dados do Funcionário ---");
            console.log(`Nome: ${nome}`);
            console.log(`Endereço: ${endereco}, Cidade: ${cidade}, Estado: ${estado}`);
            console.log(`Sexo: ${sexo}, Idade: ${idade}`);
            console.log("--- Detalhes do Cálculo ---");
            console.log(`Salário Bruto: ${salarioBruto}`);
            console.log(`Vale Transporte (2%): ${valorVT}`);
            console.log(`Vale Alimentação (5%): ${valorVA}`);
            console.log(`Plano de Saúde (10%): ${valorPS}`);
            console.log(`Total Descontos: ${totalDescontos}`);
            console.log(`Salário Líquido: ${salarioLiquido}`);
        }
    </script>
</body>
</html>