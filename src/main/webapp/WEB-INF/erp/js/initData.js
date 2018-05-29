/**
 * Created by Jiaobao on 2017/5/20.
 */
// 意向
if (intentiontype_content == undefined) {
    intentiontype_content =
        [{'意向目标客户': '意向目标客户'},
            {'意向非目标客户': '意向非目标客户'},
            {'非意向客户': '非意向客户'},
            {'意向目标客户(毛坯)': '意向目标客户(毛坯)'},
            {'意向目标客户(旧房改造)': '意向目标客户(旧房改造)'},
            {'意向非目标客户(个性化需求多)': '意向非目标客户(个性化需求多)'},
            {'意向非目标客户(别墅跃层)': '意向非目标客户(别墅跃层)'},
            {'意向非目标客户(小于50方)': '意向非目标客户(小于50方)'},
            {'未激活客户(未接/挂断/关机)': '未激活客户(未接/挂断/关机)'},
            {'非意向客户(外地)': '非意向客户(外地)'},
            {'非意向客户(本市非服务范围内)': '非意向客户(本市非服务范围内)'},
            {'非意向客户(房子已装修好)': '非意向客户(房子已装修好)'},
            {'非意向客户(未买房)': '非意向客户(未买房)'},
            {'非意向客户(局部改造)': '非意向客户(局部改造)'},
            {'非意向客户(媒体)': '非意向客户(媒体)'},
            {'非意向客户(同行)': '非意向客户(同行)'},
            {'非意向客户(其他)': '非意向客户(其他)'}]
}
// 来源
if (source_content == undefined){
    source_content = new Array();
    $.ajax({
        url: "/v1/other/getalltype?type=10",
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].enumname;
                    c = new Object();
                    c[b]= b;
                    source_content.push(c)
                }
                source_content.push({'非ICO': '非ICO'});
            }
        }
    });
}

// 美客部门
if (mobilesalesdepname_content == undefined){
    mobilesalesdepname_content = new Array();
    $.ajax({
        url: '/v1/other/getorgdepdeatil?pid=40',
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                mobilesalesdepname_data = d.data;
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].org_name;
                    c = new Object();
                    c[b]= b;
                    mobilesalesdepname_content.push(c)
                }
            }
        }
    });
}
/**
 * 市场部门
 */
if(marketdeptname_content===undefined) {
    marketdeptname_content = new Array();
    $.ajax({
        url: '/v1/other/getorgdepdeatil?pid=95',// 95：市场项目部
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                marketdeptname_data = d.data;
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].org_name;
                    c = new Object();
                    c[b]= b;
                    marketdeptname_content.push(c)
                }
            }
        }
    });
}

if (marketdempty_content == undefined){
    marketdempty_content = new Array();
    $.ajax({
        url: '/v1/user/getdepcode_userlist?code=001002018',
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].flower_name;
                    c = new Object();
                    c[b]= b;
                    marketdempty_content.push(c)
                }
            }
        }
    });
}
// 美客
if (mobilesales_content == undefined){
    mobilesales_content = new Array();
    $.ajax({
        url: '/v1/user/getdepcode_userlist?code=001002010',
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].flower_name;
                    c = new Object();
                    c[b]= b;
                    mobilesales_content.push(c)
                }
            }
        }
    });
}
// 销售部门
if (custsalesdepname_content == undefined){
    custsalesdepname_content = new Array();
    $.ajax({
        url: '/v1/other/getorgdepdeatil?pid=37',
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                custsalesdepname_data = d.data
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].org_name;
                    c = new Object();
                    c[b]= b;
                    custsalesdepname_content.push(c)
                }
            }
        }
    });
}
// 客户经理
if (custsales_content == undefined){
    custsales_content = new Array();
    $.ajax({
        url: '/v1/user/getdepcode_userlist?code=001002007&roleId=!7',
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].flower_name;
                    c = new Object();
                    c[b]= b;
                    custsales_content.push(c)
                }
            }
        }
    });
}
// 设计师
if (designers_content == undefined){
    designers_content = new Array();
    $.ajax({
        url: '/v1/user/designer_distribution?depcode=001002007',
        dataType: 'json',
        async: false,
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        success: function (d) {
            if (d.state == 200){
                var c
                for (var i = 0 ; i < d.data.length; i ++){
                    var b = d.data[i].flower_name;
                    c = new Object();
                    c[b]= b;
                    designers_content.push(c)
                }
            }
        }
    });
}
