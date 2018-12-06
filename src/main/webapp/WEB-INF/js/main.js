$(document).ready(function () {
    //为每个时间格添加时间选择器
    let $times = $(".time");
    for(var i = 0;i<$times.length;i++)
    {
        bindLayDate($times[i]);
    }
    laydate.render({
        elem:"#date",
        type:"date"
    });


    //提交
    $("#commit").click(function () {
        let date = $("#date").val();
        //校验学年是否为空
        if(date == '')
        {
            alert("学年不能为空!");
            return;
        }


        let term = $("#term").val();
        let times = [];
        let i = 0;
        $(".time").each(function () {
            times[i] = $(this).text();
            if(times[i].trim() == "")
            {
                alert("各次课时间不能为空!");
                return;
            }
            i++;
        })

        console.log( {
            'school':1,
            'date':date,
            'term':term,
            'times':times
        });

        console.log(JSON.stringify({
            'school':1,
            'date':date,
            'term':Number(term),
            'times':times
        }));

        $.ajax({
            url: "http://localhost:8080/check/addTerm",
            type: "POST",
            dataType:"JSON",
            contentType:'application/json;charset=UTF-8',//关键是要加上这行
            traditional:true,
            data:JSON.stringify({
                'school':1,
                'date':date,
                'term':Number(term),
                'times':times
            }),
            success: function (data) {
                if(data.success)
                    alert(data.data);
                else
                    alert(data.error);
            },
            error: function () {
                alert("提交失败！");
            }
        });

    });
})

function bindLayDate(e) {
    laydate.render({
        elem:e,
        range:'-',
        type:"time",
        format:"HH:mm"
    });
}