/**
 * Created by Administrator on 2015/9/21.
 */
var PAGE=(function(){
    var fn={
            /*横屏监测*/
            listenOrientation: function () {
                var supportsOrientationChange = "onorientationchange" in window,
                    orientationEvent = supportsOrientationChange ? "orientationchange" : "resize";
                $(window).bind(orientationEvent, function () {
                    var tips = $('#lateralTips');
                    if (window.orientation == 180 || window.orientation == 0) {
                        tips.fadeOut();
                    }
                    if (window.orientation == 90 || window.orientation == -90) {
                        $('html,body').scrollTop(0);
                        tips.fadeIn();
                    }
                }).trigger('orientationEvent');
            },
            //banner
            setSwiper:function(){
                var swiper = new Swiper('.swiper-container', {
                    pagination: '.swiper-pagination',
                    paginationClickable: true
                });
            },
            //tab切换
            setTab:function(){
                $('.listBtn li').click(function(){
                    $(this).addClass('curr').siblings().removeClass('curr');
                    var index=$(this).index();
                    $('.listCon>li').eq(index).addClass('show').siblings().removeClass('show');
                })
            },
            //返回顶部
            setToTop:function(){
                $(window).scroll(function(){
                    if ($(window).scrollTop()>100){
                        $('.toTop').fadeIn(1500);
                    }
                    else
                    {
                        $('.toTop').fadeOut(1500);
                    }
                });
                $('.toTop').click(function(){
                    $('body,html').animate({scrollTop:0},100);

                })

            },
            //设置弹层
            setPop:function(){
                $('.fb').click(function(){
                    $('.pop').show().addClass('curr');
                })
                $('.closed').click(function(){
                    $('.pop').hide().removeClass('curr');
                })
            },
            PAGE_NAME:function () {
            if ($('html').attr('id')) {
                return $('html').attr('id').replace(/^p\-/, '');
            } else {
                return false;
            }
    },
            //选择切换
            setChange:function(){

                $('.jfBtn a').click(function(){

                    var _index=$(this).index();
                    var Achan=$(this).attr('ch');
                    if(!$(this).hasClass('curr')){
                        $(this).addClass('curr').siblings().removeClass('curr')
                    }
                    if(_index==0){
                        if(Achan!='top'){
                            $(this).html('原价由高到低<span></span>');
                            $(this).attr('ch','top');

                        }else{
                            $(this).html('原价由低到高<span></span>');
                            $(this).attr('ch','bottom');
                        }
                    }else{
                        if(Achan!='top1'){
                            $(this).html('积分由高到低<span></span>');
                            $(this).attr('ch','top1');

                        }else{
                            $(this).html('积分由低到高<span></span>');
                            $(this).attr('ch','bottom1');
                        }
                    }
                })
            },
            //数量切换
            setNum:function(){
                $('.remove').click(function(){
                    var num=$('.num').val();
                    if(num==1){
                        num=1
                    }else{
                        num--;
                        $('.num').val(num)
                    }

                })
                $('.add').click(function(){
                    var num=$('.num').val();
                        num++;
                        $('.num').val(num)
                })
            },
            //帮助
            setHelp:function(){

                    var Index=-1;
                    $('.help li').click(function(){
                        console.log(Index)
                        if(Index==$(this).index()){
                            $('.help li').removeClass('curr');
                            Index=-1;
                        }else{
                            Index=$(this).index();
                            $(this).addClass('curr').siblings().removeClass('curr');
                        }

                    })


            },
            //点赞
            setDz:function(){
                $('.zan').live('click',function(){
                    $(this).addClass('yz')
                })
            },

            setArt:function(){
                var zdImg=$('.top ul li').eq(0).find('img');
                var zdNum=zdImg.attr('zl')

                $('.top ul li').eq(0).click(function(){

                    $(this).find('img').attr('src','img/l_icon1_h.png');


                })
                $('.top ul li').eq(2).click(function(){
                    $('.pop2').show();

                })
                $('.top ul li').eq(1).click(function(){
                    $('.pop4 #userId').val('0')
                    $('.pop4 h3').text('评论')
                    $('.pop4').show();
                    $('#saytext').attr('placeholder','');
                    $('#saytext').val('')

                })
                $('.closed').click(function(){
                    $('.pop').hide()
                })

                $('.hf').live('click',function(){
                    var id=$(this).attr('userId');
                    var holder='回复'+$(this).attr('hfUser')+':';
                    $('#saytext').attr('placeholder',holder);
                    $('#saytext').val('')
                    $('.pop4 h3').text('回复')

                    $('.pop4 #userId').val(id)
                    $('.pop4').show();
                })
                $('.pop').click(function(){
                    $('.pop').hide();
                    if($('video').hasClass('tsvideo')){
                        $('.tsvideo').get(0).pause();
                    }

                })
                $('.hfPop,.share,.tsvideo').click(function(){
                    $('#facebox').hide();
                    $('#facebox').remove();
                    return false;
                })
                $('.vImg').click(function(){
                    $('.pop5').show();
                    $('.tsvideo').get(0).play();
                })
                //查看结果
                $('.emotion').qqFace({
                    id : 'facebox', //表情盒子的ID
                    assign:'saytext', //给那个控件赋值
                    path:'face/'	//表情存放的路径
                });
                function replace_em(str){
                    str = str.replace(/\</g,'&lt;');
                    str = str.replace(/\>/g,'&gt;');
                    str = str.replace(/\n/g,'<br/>');
                    str = str.replace(/\[em_([0-9]*)\]/g,'<img src="face/$1.gif" border="0" />');
                    return str;
                }
            }





        },
        init=function(){

            if (fn.PAGE_NAME() === "index") {
                fn.listenOrientation();
                fn.setSwiper();
                fn.setTab();
                fn.setToTop();
                fn.setPop()
            }
            if (fn.PAGE_NAME() === "shop") {
                fn.setToTop();
                fn.setChange()
            }
            if(fn.PAGE_NAME()==='exc'){
                fn.setNum()

            }
            if(fn.PAGE_NAME()==='news'){


                fn.setToTop();

            }
            if(fn.PAGE_NAME()==='user'){
                $('.closed,.pop').click(function(){
                    $('.pop').hide();
                })
                $('.loCon').click(function(){
                    return false;
                })

            }
            if(fn.PAGE_NAME()==='u_help'){
                fn.setHelp()

            }
            if(fn.PAGE_NAME()==='videoL'){
                fn.setToTop();

            }
            if(fn.PAGE_NAME()==='act'){
                fn.setToTop();
                fn.setDz();
                fn.setArt()


            }
            $('.toBack,.login .closed').click(function(){
                alert('a')
               /* window.history.go(-1)*/
            })
        }
    return {
        fn:fn,
        init:init
    }
})()
$(function(){
    PAGE.init()
})