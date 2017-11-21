require(ElixerScript)

function onUpdate()
    time = gameTimer:getElapsedTime()
    radius = 100

    entity.transform:setPos(math.cos(time) * radius,
        math.sin(time) * radius,
        entity.transform:getPos().z)
end
