{
    imageName:"${imageName}",
    records:[
        <#list records as record>
        {
            name:"${record.name}",
            positionX:${record.positionX},
            positionY:${record.positionY},
            width:${record.width},
            height:${record.height},
            offsetX:${record.offsetX},
            offsetY:${record.offsetY},
            originalWidth:${record.originalWidth},
            originalHeight:${record.originalHeight},
            isRotated:${record.rotated?c}
        }<#if record_has_next>,</#if>
        </#list>
    ]
}