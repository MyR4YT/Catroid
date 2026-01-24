catroid.registerBrick("Cubic Out", "Tweening", "applyCubicOut", {"steps", "distance"}, "#E91E63")

function applyCubicOut(args)
    local steps = tonumber(args["steps"]) or 10
    local dist = tonumber(args["distance"]) or 100
    
    catroid.log("Iniciando Cubic Out: " .. dist .. "px em " .. steps .. " passos")
    
    for i = 0, steps do
        local t = i / steps
        local progress = 1 - (1 - t)^3
        catroid.moveX(progress * dist)
    end
end
