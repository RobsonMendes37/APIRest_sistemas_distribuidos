from api_client import ApiClient
import app_logic 


# --- Configurações da Aplicação ---
BASE_URL = "http://localhost:8080/api"
AUTH_CREDENTIALS = ('admin', 'senha123')



def run():
    #Função que inicializa o cliente e executa o menu.
    api = ApiClient(BASE_URL, AUTH_CREDENTIALS)

    while True:
        app_logic.limpar_tela()
        print("--- CLIENTE PYTHON - API DE RH ---")
        print("1. Gerenciar Enfermeiros")
        print("2. Gerenciar Médicos Efetivos")
        print("3. Gerenciar Médicos Plantonistas")
        print("4. Listar TODOS os funcionários")
        print("0. Sair")
        opcao = input("Escolha uma opção: ")


        if opcao == '1':
            app_logic.gerenciar_entidade(api, 'Enfermeiro', 'enfermeiros')
        elif opcao == '2':
            app_logic.gerenciar_entidade(api, 'Médico Efetivo', 'medicos-efetivos')
        elif opcao == '3':
            app_logic.gerenciar_entidade(api, 'Médico Plantonista', 'medicos-plantonistas')
        elif opcao == '4':
            app_logic.limpar_tela()
            print("--- Listando todos os funcionários ---")
            response = api.request('GET', 'rh/funcionarios')
            app_logic.processar_resposta(response)
            app_logic.press_enter_para_continuar()
        elif opcao == '0':
            print("Encerrando...")
            break
        else:
            print("Opção inválida!")
            app_logic.press_enter_para_continuar()



if __name__ == "__main__":
    run()