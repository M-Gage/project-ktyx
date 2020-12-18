/*为分类树加载子节点*/


/*加载分类树*/
/*function loadType() {
    $.ajax({
        url: "/type",
        type: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var json = [],
                    root = result.data,
                    len = root.length;

                for (var i = 0; i < len; i++) {
                    if (root[i].typeId.toString().length === 4) {
                        root[i].text = root[i].typeName;
                        root[i].id = root[i].typeId;
                        json.push(root[i]);
                        loadChildNode(result.data, root[i]);
                    }
                }

                $("#comboTree").bootstrapCombotree({
                    defaultLable: '请 选 择 商 品 分 类',              //默认按钮上的文本
                    data: json,                                     //data应符合实例的data格式
                    showIcon: true,                                 //显示图标
                    width: 130,                                     //下拉列表宽度
                    minWidth: 130,
                    name: 'list',                                   //combotree值域的name，可以用在表单提交
                    maxItemsDisplay: 1,                             //按钮上最多显示多少项，如果超出这个数目，将会以‘XX项已被选中代替’
                    selectToCheck: true,                            //为了兼容移动设备，点击属性菜单项时自动选中节点
                    onCheck: function (node) {                      //树形菜单被选中时触发事件, node为选中的那个节点
                        if (node.nodes === undefined) {
                            $('.btn-group').attr('class', 'btn-group');
                            $('.btn-group').find('button:first').attr('aria-expanded', false);
                        }
                    }
                });
            } else {
                layer.msg(result.message)
            }

        }
    });
}*/

var typeTree = {
    data:{
        el:"",
        title_el:"",
        id_el:"",
        url:"/type",
        clickTable:false
    },
    /**
     * 初始化部门树
     * @returns {typeTree}
     */
    initTree : function(){
        $.ajax({
            url:typeTree.data.url,
            type:"get",
            dataType: 'json',
            success: function (result) {
                var json = [],
                    root = result.data,
                    len = root.length;
                for (var i = 0; i < len; i++) {
                    if (root[i].typeId.toString().length === 4) {
                        root[i].text = root[i].typeName;
                        root[i].id = root[i].typeId;
                        json.push(root[i]);
                        loadChildNode(result.data, root[i]);
                    }
                }
                function loadChildNode(root, node) {
                    var reg = new RegExp("^" + node.typeId + "..$"),
                        len = root.length;
                    for (var i = 0; i < len; i++) {
                        if (reg.test(root[i].typeId.toString())) {
                            if (!node.nodes) {
                                node.nodes = [];
                            }
                            node.nodes.push(root[i]);
                            root[i].text = root[i].typeName;
                            root[i].id = root[i].typeId;
                            loadChildNode(root, root[i]);
                        }
                    }
                }


                $(typeTree.data.el).treeview({
                    // levels: 1,
                    data: json,
                    onNodeSelected: function (event, data) {
                        $(typeTree.data.id_el).val(data.typeId);
                        console.log(data.typeId);
                        //判断是否需要表格点击
                        if(typeTree.data.clickTable){
                            typeTree.tableData.urlParam=data.typeId;
                            typeTree.destroyTable().initTable();
                        }
                        $(typeTree.data.title_el).html(data.text);
                        if(!$(".tree_content").hasClass('open')){
                            $(".tree_content").parent().removeClass('open');
                        }
                    },
                    onNodeUnselected: function (event,data) {
                        typeTree.destoryTree();
                    }
                });
            },
            error: function () {
                layer.msg("商品分类加载失败",{icon:2})
            }
        });
        return this;
    },
    destoryTree :function(){
        $(this.data.id_el).val('');
        return this;
    }
};