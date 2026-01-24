catroid.registerBrick("Cubic Out", "Tweening", "cubicOut", {"steps"}, "#FF00FF")

function cubicOut(args)
    local steps = tonumber(args["steps"]) or 10
    catroid.log("Iniciando Tween Cubic Out com " .. steps .. " passos")
    
    for i = 0, steps, 1 do
        local t = i / steps
        local val = 1 - (1 - t)^3
        catroid.log("Interpolação: " .. val)
        catroid.moveX(val * 100)
    end
end
