import os
import json
from api_client import ApiClient

def limpar_tela():
    """Limpa o terminal."""
    os.system('cls' if os.name == 'nt' else 'clear')

def press_enter_para_continuar():
    #Pausa a execução até que o Enter seja pressionado.
    input("\nPressione Enter para continuar...")




#Interpreta a resposta da API e exibe o resultado
def processar_resposta(response):

    if response is None: # Erro de conexão
        return

    # --- SUCESSO ---
    if 200 <= response.status_code < 300:
        print(f"\n Sucesso (Status: {response.status_code})")
        if response.status_code == 204: # No Content
            print("Operação concluída com sucesso.")
        else:
            print(json.dumps(response.json(), indent=2, ensure_ascii=False))
        return


    # --- ERROS ---
    print(f"\n Erro (Status: {response.status_code})")



    # Tratamento de erros específicos conhecidos
    if response.status_code == 404:
        print(" O recurso solicitado não foi encontrado no servidor.")
        print("Verifique se a matrícula ou o endpoint estão corretos.")
        return
    
    if response.status_code == 401:
        print("Autenticação falhou. Verifique o usuário e a senha no cliente.")
        return

    if response.status_code == 403:
        print("Acesso proibido. Você não tem permissão para realizar esta operação.")
        return



    # Tratamento para erros que retornam um corpo JSON (como 400 Bad Request)
    try:
        print("Detalhes do erro:")
        print(json.dumps(response.json(), indent=2, ensure_ascii=False))
    except json.JSONDecodeError:
        # Fallback para erros inesperados que não retornam JSON
        print("O servidor retornou um erro, mas a resposta não estava no formato JSON.")




#Coleta dados do usuário para criar/atualizar um funcionário.
def coletar_dados_funcionario(tipo_funcionario, is_update=False):
    print(f"\n--- Preencha os dados para {tipo_funcionario} ---")

    dados = {}

    if not is_update:
        dados['matricula'] = input("Matrícula: ")
    dados['nome'] = input("Nome: ")

    if tipo_funcionario == 'Enfermeiro':    #Enfermeiro
        dados['coren'] = input("COREN: ")
        dados['salarioBase'] = float(input("Salário Base: "))

    elif tipo_funcionario in ['Médico Efetivo', 'Médico Plantonista']:   #Médico Plantonista,Efetivo
        dados['crm'] = input("CRM: ")

        if tipo_funcionario == 'Médico Efetivo':     # Médico Efetivo
            dados['salarioBase'] = float(input("Salário Base: "))

        else:
            dados['horasTrabalhadas'] = int(input("Horas Trabalhadas: "))
            dados['valorHoraPlatao'] = float(input("Valor por Hora de Plantão: "))
    return dados





def gerenciar_entidade(api: ApiClient, nome_entidade: str, endpoint: str):
    #Loop principal para o submenu.
    while True:
        limpar_tela()
        print(f"--- Gerenciando {nome_entidade} ---")
        print("1. Listar todos")
        print("2. Consultar por matrícula")
        print("3. Contratar novo")
        print("4. Atualizar existente")
        print("5. Demitir")
        print("0. Voltar")
        opcao = input("Escolha uma opção: ")
        
        if opcao == '1': # Listar
            response = api.request('GET', endpoint)
            processar_resposta(response)
        elif opcao == '2': # Consultar
            matricula = input("Digite a matrícula: ")
            response = api.request('GET', f"{endpoint}/{matricula}")
            processar_resposta(response)
        elif opcao == '3': # Contratar
            dados = coletar_dados_funcionario(nome_entidade)
            response = api.request('POST', endpoint, data=dados)
            processar_resposta(response)
        elif opcao == '4': # Atualizar
            matricula = input("Digite a matrícula para atualizar: ")
            dados = coletar_dados_funcionario(nome_entidade, is_update=True)
            response = api.request('PUT', f"{endpoint}/{matricula}", data=dados)
            processar_resposta(response)
        elif opcao == '5': # Demitir
            matricula = input("Digite a matrícula para demitir: ")
            response = api.request('DELETE', f"{endpoint}/{matricula}")
            processar_resposta(response)
        elif opcao == '0': # Voltar
            break
        else:
            print("Opção inválida!")
        
        press_enter_para_continuar()