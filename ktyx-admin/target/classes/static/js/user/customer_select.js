function customerList(ctrlName,staffId,deptId,isDeptManager) {
    $.ajax({
        url: "/customer/subordinates",
        type: "get",
        data: {
            staffId: staffId,
            deptId:deptId,
            isDeptManager:isDeptManager
        },
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code === 200) {
                var customerList = result.data, List = $(ctrlName);
                List.html("");
                for (var i in customerList) {
                    List.append("<option value='" + customerList[i].customerId + "'>" + customerList[i].customerName + "</option>")
                }
                List.selectpicker("refresh");
            }
        }
    });
}
