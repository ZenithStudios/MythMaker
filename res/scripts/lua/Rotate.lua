model = entity:getComponent(MeshRendererComponent):getModel()

function onUpdate()
    model:incrementTextureIndex()
    entity.transform:addRot(0, -0.5, 0)
end

