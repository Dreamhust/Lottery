function getMoreNews()
{
		var number = LotteryNews.GetMoreNewsNumber();
	    var count = 0;
		var topList = document.getElementById("news_list");
	    for(var i=0;i<number;i++)
	    {
	        var str_js_news = LotteryNews.GetMoreNews(i);
	        var news_obj = JSON.parse(str_js_news);
			console.log("news: " + str_js_news);
			topList.appendChild(createOneNews(news_obj));
	    }
}

function createOneNews(news)
{
	var li=document.createElement("li");
    li.setAttribute("class","am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right pet_hd_list");
    var html = "<a href=\""+news.url+"\" class=\"pet_hd_block\"><div class=\"pet_hd_block_title\">"+news.title+"</div><div class=\"pet_hd_block_map\">"+news.content.substring(0,15)+"...<查看详情>"+"</div><div class=\"pet_hd_block_tag\"><span class=\"hd_tag_jh\">新闻</span>"+news.publishDateStr+"</div></a>"
	var div=document.createElement("div");
    div.innerHTML=html;
    li.appendChild(div);
	return li;
}
