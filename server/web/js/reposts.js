$(function () {
    setTable();
    $("#p").window('close');
});
//get the data table
function setTable() {
    $('#reports').datagrid({
        url:'/reportServlet?method=getReportByCondition',
        fitColumns:true,
        width:900,
        method:"post",
        loadMsg:"正在请求数据...",
        //page division
        pagination:true,
        pageNumber:1,
        pageSize:10,
        pageList:[5,10],
        columns:[[
            {field:'id',title:'举报编号',width:50},
            {field:'ruser',title: '举报用户',width:100},
            {field:'reason',title:'举报理由',width:200},
            {field:'evidence',title:'证据截图',formatter:function (value,row,index) {
                return '<img src="img/'+value+'" alt="截图" width="120px" height="150px" onclick="showEvidence(\''+value+'\')">';
            }},
            {field:'action',title:'操作',formatter:function (value,row,index) {
                //row.empno当前员工的编号
                let html = '<span onclick="punishUser('+row.id+',true,'+row.ruser+')" class="spanCik">惩罚</span>&nbsp;&nbsp;<span class="spanCik" onclick="deleteReport('+row.id+',false)">忽略</span>';
                return html;
            }}
        ]]
    });
}

function showEvidence(picture) {
    $("#p").window('open');
    $("#showPicture").attr("src","/img/"+picture);
}

function punishUser(reportId,ifPunish,userId) {
    $.messager.confirm('确认','您确认要惩罚该用户吗？',function (flag) {
        if(flag){
            $.ajax({
                method: "post",
                url:"/deleteReport",
                data:{"reportId":reportId,"ifPunish":ifPunish,"userId":userId},
                success:function (data) {
                    $('#reports').datagrid('load');
                }
            });
        }
    });
}

function deleteReport(reportId,ifPunish) {
    $.ajax({
        method: "post",
        url:"/deleteReport",
        data:{"reportId":reportId,"ifPunish":ifPunish},
        success:function (data) {
            $('#reports').datagrid('load');
        }
    });
}