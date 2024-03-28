# ADD in VM OPTIONS
Abra as configurações de execução da sua aplicação clicando no menu "Run" e selecionando "Edit Configurations...".
Na janela de configurações de execução, encontre a configuração da sua aplicação.
No campo "VM options", adicione "--add-exports=java.base/sun.security.util=ALL-UNNAMED,
--add-opens java.base/java.io=ALL-UNNAMED"

Clique em "Apply" e em "OK" para salvar as configurações.