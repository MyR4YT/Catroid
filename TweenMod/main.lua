-- Registrar o bloco na nova categoria "Tweening"
-- Sintaxe: nome do bloco, categoria, função lua
catroid.registerBrick("Move Cubic Out", "Tweening", "performCubicOut")

-- Função matemática Cubic Out: Começa rápido e desacelera no final
function easeOutCubic(x)
    return 1 - (1 - x)^3
end

function performCubicOut()
    print("Iniciando animação Cubic Out...")
    
    -- Simulação de um loop de animação (0 a 100%)
    -- Nota: Num cenário real, isso seria ligado ao "update" do jogo
    for i = 0, 10, 1 do
        local t = i / 10.0 -- Tempo normalizado (0.0 a 1.0)
        local valorSuavizado = easeOutCubic(t)
        
        -- Calcula a posição final (ex: mover 100 passos)
        local passos = valorSuavizado * 100
        
        print("Tempo: " .. t .. " -> Posicao: " .. passos)
        
        -- Se a API existisse, seria algo como:
        -- catroid.setX(passos)
    end
    print("Animação concluída!")
end
