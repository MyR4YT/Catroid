-- Log de inicialização
print("Mod System: Iniciando Ultimate Test Mod...")

function onStart()
    -- Exemplo de funções que este mod poderia chamar
    Catroid.UI.showToast("Mod Carregado com Sucesso!")
    Catroid.Game.setBackground("Galaxy")
    
    -- Manipulação de atores
    local player = Catroid.Game.getObject("Hero")
    if player then
        player:setScale(2.0) -- Dobra o tamanho
        player:setX(0)
    end
end

onStart()
