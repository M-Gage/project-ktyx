function staffList(ctrlName) {
    var flag = false;
    if ($(ctrlName).find("option").length > 5) {
        return flag;
    }
     $.ajax({
        url: "/staff/subordinates",
        type: "get",
        async:false,
        success: function (result) {
            if (result.code === 200) {
                var staff = $(ctrlName), staffList = result.data, len = staffList.length;
                staff.html('');
                for (var i = 0; i < len; i++) {
                    staff.append("<option value='" + staffList[i].staffId + " "+staffList[i].deptId+" "+staffList[i].isDeptManager+"'>" + staffList[i].staffName + "</option>")
                }
                staff.selectpicker("refresh");
                flag = true;
            } else {
                layer.msg("系统异常，获取下级员工失败",{icon:2})
            }
        }
    });
    return flag;
}