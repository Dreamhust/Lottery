function getTopNews()
{
		var number = LotteryNews.GetTopNewsNumber();
	    var count = 0;
	    var liStr="";
	    for(var i=0;i<number;i++)
	    {
	        var str_js_news = LotteryNews.GetTopNews(i);
	        var news_obj = JSON.parse(str_js_news);
			console.log("news: " + str_js_news);
	        if(news_obj.imageUrls==null)
	        {
				console.log("skip news for null images: " + news_obj.title);
				addMidNews(news_obj);
	            continue;
	        }
	        else
	        {
	            var imageUrl = null;
	            for(var j=0;j<news_obj.imageUrls.length;j++)
	            {
	                if(news_obj.imageUrls[j].indexOf("http")>=0)
	                {
	                    imageUrl = news_obj.imageUrls[j];
	                    break;
	                }
					else
					{
						console.log("skip news for empty images: " + news_obj.title);
					}
	            }
	            if(imageUrl!=null&&count<3)
	            {
					var img_id="top_img_"+count;
					var time_id = "top_time_"+count;
					var title_id = "top_title_"+count; 
					var link_id = "top_link_"+count;
					var img = document.getElementById(img_id);
					img.src=imageUrl;
					document.getElementById(time_id).text=news_obj.publishDateStr;
					document.getElementById(title_id).text=news_obj.title;
					document.getElementById(link_id).setAttribute("href",news_obj.url);
					count++;
	            }
				else
				{
					addMidNews(news_obj);
				}
	        }
	    }
}
function createOnePictureNews(news)
{
	var img = null;
	for(var i=0;i<news.imageUrls.length;i++)
	{
		if(news.imageUrls[i].indexOf("http")>=0)
	      {
	         img = news.imageUrls[i];
	         break;
	      }
		else
		{
			createNoPictureNews(news);
			return;
		}
	}
	var li=document.createElement("li");
    li.setAttribute("class","am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right pet_list_one_block");
    var html = "<div class='pet_list_one_info'><div class='pet_list_one_info_l'><div class='pet_list_one_info_ico'><img src='img/a1.png' alt=''></div><div class='pet_list_one_info_name'>"+news.posterScreenName+"</div></div><div class='pet_list_one_info_r'><div class='pet_list_tag pet_list_tag_xxs'>新闻</div></div></div><div class='am-u-sm-8 am-list-main pet_list_one_nr'><h3 class='am-list-item-hd pet_list_one_bt'><a href=\""+news.url+"\" class=''>"+news.title+"</a></h3><div class='am-list-item-text pet_list_one_text'>"+news.content+"</div></div><div class='am-u-sm-4 am-list-thumb'><a href=\""+news.url+"\" class=''><img src=\""+img+"\" class='pet_list_one_img' alt=''/></a></div>"
	var div=document.createElement("div");
    div.innerHTML=html;
    li.appendChild(div);
	return li;
}
function createNoPictureNews(news)
{
	var li=document.createElement("li");
    li.setAttribute("class","am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right pet_list_one_block");
    var html = "<div class='pet_list_one_info'><div class='pet_list_one_info_l'><div class='pet_list_one_info_ico'><img src='img/a2.png' alt=''></div><div class='pet_list_one_info_name'>"+news.posterScreenName+"</div></div><div class='pet_list_one_info_r'><div class='pet_list_tag pet_list_tag_zzs'>趣闻</div></div></div><div class='am-list-main'><h3 class='am-list-item-hd pet_list_one_bt'><a href=\""+news.url+"\" class=''>"+news.title+"</a></h3><div class='am-list-item-text pet_list_two_text'>"+news.content+"</div></div>"
	var div=document.createElement("div");
    div.innerHTML=html;
    li.appendChild(div);
	return li;
}
function addMidNews(news)
{
	var news_list = document.getElementById("mid_news_list");
	news_list
	var li = null;
	if(news.imageUrls==null)
	{
		li = createNoPictureNews(news);
	}
	else
	{
		if(news.imageUrls.length>=1)
		{
			li = createOnePictureNews(news);
		}
	}
	if(li!=null)
	{
		news_list.appendChild(li);
	}
	
}
