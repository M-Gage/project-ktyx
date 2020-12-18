var deptTree = {
    data: {
        el: "",
        title_el: "",
        id_el: "",
        url: "/dept/list",
        clickTable: false
    },
    /**
     * 初始化部门树
     * @returns {deptTree}
     */
    initTree: function () {
        $.ajax({
            url: deptTree.data.url,
            type: "get",
            dataType: 'json',
            success: function (result) {
                var json = [],
                    root = result.data,
                    len = root.length;
                for (var i = 0; i < len; i++) {
                    if (root[i].deptId === $("#userDeptID").val()) {
                        root[i].text = root[i].deptName;
                        json.push(root[i]);
                        loadChildNode(result.data, root[i]);
                    }
                }

                function loadChildNode(root, node) {
                    var reg = new RegExp("^" + node.deptId + "..$"),
                        len = root.length;
                    for (var i = 0; i < len; i++) {
                        if (reg.test(root[i].deptId.toString())) {
                            if (!node.nodes) {
                                node.nodes = [];
                            }
                            node.nodes.push(root[i]);
                            root[i].text = root[i].deptName;
                            loadChildNode(root, root[i]);
                        }
                    }
                }

                $(deptTree.data.el).treeview({
                    // levels: 1,
                    data: json,
                    onNodeSelected: function (event, data) {
                        $(deptTree.data.id_el).val(data.deptId);
                        console.log(data.deptId);
                        //判断是否需要表格点击
                        if (deptTree.data.clickTable) {
                            deptTree.tableData.urlParam = data.deptId;
                            deptTree.destroyTable().initTable();
                        }
                        $(deptTree.data.title_el).html(data.text);
                        if (!$(".tree_content").hasClass('open')) {
                            $(".tree_content").parent().removeClass('open');
                        }
                    },
                    onNodeUnselected: function (event, data) {
                        deptTree.destoryTree();
                    }
                });
            },
            error: function () {
                layer.msg("部门树形结构加载失败", {icon: 2})
            }
        });
        return this;
    },
    destoryTree: function () {
        $(this.data.id_el).val('');
        return this;
    }
};