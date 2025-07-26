import requests

class ApiClient:
    #classe lida com toda a comunicação com a API do RH."""

    def __init__(self, base_url, auth_credentials):
        self.base_url = base_url
        self.auth = auth_credentials


    #Metodo geral pra fazer qualquer requisição pro servidor
    def request(self, method, endpoint, data=None):
        try:
            url = f"{self.base_url}/{endpoint}"
            response = requests.request(method, url, json=data, auth=self.auth)
            return response
        except requests.exceptions.RequestException as e:
            # Retorna None em caso de erro de conexão para ser tratado por quem chamou
            print(f"\n Erro de conexão: {e}")
            return None




    # è isso aqui, mas reutilavel:
    #     def get(self, endpoint):
    #         try:
    #             url = f"{self.base_url}/{endpoint}"
    #             # A única linha diferente é esta:
    #             response = requests.get(url, auth=self.auth)
    #             return response
    #         except requests.exceptions.RequestException as e:
    #             print(f"\n Erro de conexão: {e}")
    #             return None
    #
    # def post(self, endpoint, data):
    #     try:
    #         url = f"{self.base_url}/{endpoint}"
    #         # A única linha diferente é esta:
    #         response = requests.post(url, json=data, auth=self.auth)
    #         return response
    #     except requests.exceptions.RequestException as e:
    #         print(f"\n Erro de conexão: {e}")
    #         return None
    #
    # def put(self, endpoint, data):
    #     try:
    #         url = f"{self.base_url}/{endpoint}"
    #         # A única linha diferente é esta:
    #         response = requests.put(url, json=data, auth=self.auth)
    #         return response
    #     except requests.exceptions.RequestException as e:
    #         print(f"\n Erro de conexão: {e}")
    #         return None