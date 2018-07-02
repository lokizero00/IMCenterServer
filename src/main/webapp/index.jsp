<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = "//" + request.getHeader("host")+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, maximum-scale=1">
<title>Home</title>
<link rel="icon" href="favicon.png" type="image/png">
<link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>static/css/style.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>static/css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>static/css/animate.css" rel="stylesheet" type="text/css">

<!--[if lt IE 9]>
	<script src="<%=basePath%>static/custom/respond-1.1.0.min.js"></script>
	<script src="<%=basePath%>static/custom/html5shiv.js"></script>
	<script src="<%=basePath%>static/custom/html5element.js"></script>
<![endif]-->

</head>
<body>

<!--Header_section-->
<header id="header_wrapper">
  <div class="container">
    <div class="header_box">
      <div class="logo"><a href="#"><img src="images/logo.png" alt="logo"></a></div>
	  <nav class="navbar navbar-inverse" role="navigation">
      <div class="navbar-header">
        <button type="button" id="nav-toggle" class="navbar-toggle" data-toggle="collapse" data-target="#main-nav"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        </div>
	    <div id="main-nav" class="collapse navbar-collapse navStyle">
			<ul class="nav navbar-nav" id="mainNav">
			  <li class="active"><a href="#hero_section" class="scroll-link">首页</a></li>
			  <li><a href="#aboutUs" class="scroll-link">关于我们</a></li>
			  <li><a href="#service" class="scroll-link">企业服务</a></li>
			  <li><a href="#Portfolio" class="scroll-link">企业产品</a></li>
			  <li><a href="#clients" class="scroll-link">客户端</a></li>
			  <li><a href="#team" class="scroll-link">企业团队</a></li>
			  <li><a href="#contact" class="scroll-link">联系我们</a></li>
			</ul>
      </div>
	 </nav>
    </div>
  </div>
</header>
<!--Header_section-->

<!--Hero_Section-->
<section id="hero_section" class="top_cont_outer">
  <div class="hero_wrapper">
    <div class="container">
      <div class="hero_section">
        <div class="row">
          <div class="col-lg-5 col-sm-7">
            <div class="top_left_cont zoomIn wow animated">
              <h2>互联制造，诚意商机，就在这里</h2>
              <p>用信息技术为制造领域企业创造更加优质、高效和可靠的供求对接和交易合作机会。依托大数据，打造精准智能推荐系统，构建互联制造信息化平台，融合供求双方资源，重建制造领域供应链闭环，实现采购方、供应方和平台方的三方共赢生态。</p>
              <a href="#service" class="read_more2">Read more</a> </div>
          </div>
          <div class="col-lg-7 col-sm-5">
			<img src="images/app_home.png" class="zoomIn wow animated" alt="" style="width: 300px;height: 500px;margin-left: 150px;"/>
		  </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!--Hero_Section-->

<section id="aboutUs"><!--Aboutus-->
<div class="inner_wrapper">
  <div class="container">
    <h2>关于我们</h2>
    <div class="inner_section">
	<div class="row">
      <div class=" col-lg-4 col-md-4 col-sm-4 col-xs-12 pull-right"><img src="images/about-img.jpg" class="img-circle delay-03s animated wow zoomIn" alt=""></div>
      	<div class=" col-lg-7 col-md-7 col-sm-7 col-xs-12 pull-left">
        	<div class=" delay-01s animated fadeInDown wow animated">
			<h3>大数据支撑、智能制造</h3><br/>
            <p>绿驱是一家致力于制造业领域的互联网信息技术服务公司，结合丰富的制造企业实践及管理经验，以及强大的互联网技术开发能力，不断地开发新型互联网信息技术产品，填补制造业市场的互联网信息技术空缺，为众企业创造便捷、优质、互赢的合作环境。</p>
</div>
<div class="work_bottom"> <span>了解更多</span> <a href="#contact" class="contact_btn">联系我们</a> </div>
	   </div>

      </div>


    </div>
  </div>
  </div>
</section>
<!--Aboutus-->


<!--Service-->
<section  id="service">
  <div class="container">
    <h2>企业服务</h2>
    <div class="service_wrapper">
      <div class="row">
        <div class="col-lg-4">
          <div class="service_block">
            <div class="delay-03s animated wow  zoomIn"><img src="images/icon_purchase.png"></img></div>
            <h3 class="animated fadeInUp wow">采购需求</h3>
            <p class="animated fadeInDown wow">查找、发布采购需求的地方，在这里，供应商会主动匹配客户的需求，竞价，向客户呈现意向度，申请高质量线上对接。</p>
          </div>
        </div>
        <div class="col-lg-4 borderLeft">
          <div class="service_block">
            <div class="delay-03s animated wow zoomIn"> <img src="images/icon_supply.png"></img></div>
            <h3 class="animated fadeInUp wow">供应资源</h3>
            <p class="animated fadeInDown wow">查找、发布供应资源的地方，在这里，客户采购会主动匹配供应商的供应资源，向供应商呈现意向度，申请高质量线上对接。</p>
          </div>
        </div>
        <div class="col-lg-4 borderLeft">
          <div class="service_block">
            <div class="delay-03s animated wow zoomIn"> <img src="images/icon_guara.png"></div>
            <h3 class="animated fadeInUp wow">担保交易</h3>
            <p class="animated fadeInDown wow">针对于非对公交易、异地小额对公交易，进行线上担保，在保障需方资金安全的同时，也给发货方带来可靠的发货保障。</p>
          </div>
        </div>
      </div>
	   <div class="row borderTop" style="display: none;">
        <div class="col-lg-4 mrgTop">
          <div class="service_block">
            <div class="service_icon delay-03s animated wow  zoomIn"> <span><i class="fa fa-dropbox"></i></span> </div>
            <h3 class="animated fadeInUp wow">Concept</h3>
            <p class="animated fadeInDown wow">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text.</p>
          </div>
        </div>
        <div class="col-lg-4 borderLeft mrgTop">
          <div class="service_block">
            <div class="service_icon icon2  delay-03s animated wow zoomIn"> <span><i class="fa fa-slack"></i></span> </div>
            <h3 class="animated fadeInUp wow">User Research</h3>
            <p class="animated fadeInDown wow">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text.</p>
          </div>
        </div>
        <div class="col-lg-4 borderLeft mrgTop">
          <div class="service_block">
            <div class="service_icon icon3  delay-03s animated wow zoomIn"> <span><i class="fa fa-users"></i></span> </div>
            <h3 class="animated fadeInUp wow">User Experience</h3>
            <p class="animated fadeInDown wow">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!--Service-->




<!-- Portfolio -->
<section id="Portfolio" class="content">

  <!-- Container -->
  <div class="container portfolio_title">

    <!-- Title -->
    <div class="section-title">
      <h2>企业产品</h2>
    </div>
    <!--/Title -->

  </div>
  <!-- Container -->

  <div class="portfolio-top"></div>

  <!-- Portfolio Filters -->
  <div class="portfolio">

    <div id="filters" class="sixteen columns">
      <ul class="clearfix">
        <li><a id="all" href="#" data-filter="*" class="active">
          <h5>All</h5>
          </a></li>
        <li><a class="" href="#" data-filter=".prototype">
          <h5>Prototype</h5>
          </a></li>
        <li><a class="" href="#" data-filter=".design">
          <h5>Design</h5>
          </a></li>
        <li><a class="" href="#" data-filter=".android">
          <h5>Android</h5>
          </a></li>
        <li><a class="" href="#" data-filter=".appleIOS">
          <h5>Apple IOS</h5>
          </a></li>
        <li><a class="" href="#" data-filter=".web">
          <h5>Web App</h5>
          </a></li>
      </ul>
    </div>
    <!--/Portfolio Filters -->

    <!-- Portfolio Wrapper -->
    <div class="isotope fadeInLeft animated wow" style="position: relative; overflow: hidden; height: 480px;" id="portfolio_wrapper">

      <!-- Portfolio Item -->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(0px, 0px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four   appleIOS isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic1.jpg"  alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">SMS Mobile App</h4>
          </div>
        </div>
        </div>
      <!--/Portfolio Item -->

      <!-- Portfolio Item-->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(337px, 0px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four  design isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic2.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">Finance App</h4>
          </div>
        </div>
      </div>
      <!--/Portfolio Item -->

      <!-- Portfolio Item -->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(674px, 0px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four  design  isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic3.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">GPS Concept</h4>
          </div>
        </div>
      </div>
      <!--/Portfolio Item-->

      <!-- Portfolio Item-->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(1011px, 0px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four  android  prototype web isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic4.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">Shopping</h4>
          </div>
        </div>
      </div>
      <!-- Portfolio Item -->

      <!-- Portfolio Item -->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(0px, 240px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four  design isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic5.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">Managment</h4>
          </div>
        </div>
      </div>
      <!--/Portfolio Item -->

      <!-- Portfolio Item -->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(337px, 240px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four  web isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic6.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">iPhone</h4>
          </div>
        </div>
      </div>
      <!--/Portfolio Item -->

      <!-- Portfolio Item  -->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(674px, 240px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four  design web isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic7.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
            <h4 class="project_name">Nexus Phone</h4>
          </div>
        </div>
       </div>
      <!--/Portfolio Item -->

      <!-- Portfolio Item -->
      <div style="position: absolute; left: 0px; top: 0px; transform: translate3d(1011px, 240px, 0px) scale3d(1, 1, 1); width: 337px; opacity: 1;" class="portfolio-item one-four   android isotope-item">
        <div class="portfolio_img"> <img src="images/portfolio_pic8.jpg" alt="Portfolio 1"> </div>
        <div class="item_overlay">
          <div class="item_info">
				<h4 class="project_name">Android</h4>
          </div>
        </div>
        </a> </div>
      <!--/Portfolio Item -->

    </div>
    <!--/Portfolio Wrapper -->

  </div>
  <!--/Portfolio Filters -->

  <div class="portfolio_btm"></div>


  <div id="project_container">
    <div class="clear"></div>
    <div id="project_data"></div>
  </div>


</section>
<!--/Portfolio -->

<section class="page_section" id="clients"><!--page_section-->
  <h2>移动客户端</h2>
<!--page_section-->
<div class="client_logos"><!--client_logos-->
  <div class="container">
    <ul class="fadeInRight animated wow">
      <li><a href="javascript:void(0)"><img src="images/client_logo1.png" alt=""></a></li>
      <li><a href="javascript:void(0)"><img src="images/client_logo2.png" alt=""></a></li>
      <li><a href="javascript:void(0)"><img src="images/client_xcx.png" alt=""></a></li>
    </ul>
  </div>
</div>
</section>
<!--client_logos-->

<section class="page_section team" id="team"><!--main-section team-start-->
  <div class="container">
    <h2>Team</h2>
    <h6>Lorem ipsum dolor sit amet, consectetur adipiscing.</h6>
    <div class="team_section clearfix">
      <div class="team_area">
        <div class="team_box wow fadeInDown delay-03s">
          <div class="team_box_shadow"><a href="javascript:void(0)"></a></div>
          <img src="images/team_pic1.jpg" alt="">
          <ul>
            <li><a href="javascript:void(0)" class="fa fa-twitter"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-facebook"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-pinterest"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-google-plus"></a></li>
          </ul>
        </div>
        <h3 class="wow fadeInDown delay-03s">Tom Rensed</h3>
        <span class="wow fadeInDown delay-03s">Chief Executive Officer</span>
        <p class="wow fadeInDown delay-03s">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin consequat sollicitudin cursus. Dolor sit amet, consectetur adipiscing elit proin consequat.</p>
      </div>
      <div class="team_area">
        <div class="team_box  wow fadeInDown delay-06s">
          <div class="team_box_shadow"><a href="javascript:void(0)"></a></div>
          <img src="images/team_pic2.jpg" alt="">
          <ul>
            <li><a href="javascript:void(0)" class="fa fa-twitter"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-facebook"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-pinterest"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-google-plus"></a></li>
          </ul>
        </div>
        <h3 class="wow fadeInDown delay-06s">Kathren Mory</h3>
        <span class="wow fadeInDown delay-06s">Vice President</span>
        <p class="wow fadeInDown delay-06s">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin consequat sollicitudin cursus. Dolor sit amet, consectetur adipiscing elit proin consequat.</p>
      </div>
      <div class="team_area">
        <div class="team_box wow fadeInDown delay-09s">
          <div class="team_box_shadow"><a href="javascript:void(0)"></a></div>
          <img src="images/team_pic3.jpg" alt="">
          <ul>
            <li><a href="javascript:void(0)" class="fa fa-twitter"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-facebook"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-pinterest"></a></li>
            <li><a href="javascript:void(0)" class="fa fa-google-plus"></a></li>
          </ul>
        </div>
        <h3 class="wow fadeInDown delay-09s">Lancer Jack</h3>
        <span class="wow fadeInDown delay-09s">Senior Manager</span>
        <p class="wow fadeInDown delay-09s">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin consequat sollicitudin cursus. Dolor sit amet, consectetur adipiscing elit proin consequat.</p>
      </div>
    </div>
  </div>
</section>
<!--/Team-->
<!--Footer-->
<footer class="footer_wrapper" id="contact">
  <div class="container">
    <section class="page_section contact" id="contact">
      <div class="contact_section">
        <h2>Contact Us</h2>
        <div class="row">
          <div class="col-lg-4">

          </div>
          <div class="col-lg-4">

          </div>
          <div class="col-lg-4">

          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-4 wow fadeInLeft">
		 <div class="contact_info">
                            <div class="detail">
                                <h4>公司地址</h4>
                                <p>太仓市城厢镇上海东路1号1802室</p>
                            </div>
                            <div class="detail">
                                <h4>联系我们</h4>
                                <p>+86 17625364866</p>
                            </div>
                            <div class="detail">
                                <h4>Email us</h4>
                                <p>support@sitename.com</p>
                            </div>
                        </div>



          <ul class="social_links">
            <li class="twitter animated bounceIn wow delay-02s"><a href="javascript:void(0)"><i class="fa fa-twitter"></i></a></li>
            <li class="facebook animated bounceIn wow delay-03s"><a href="javascript:void(0)"><i class="fa fa-facebook"></i></a></li>
            <li class="pinterest animated bounceIn wow delay-04s"><a href="javascript:void(0)"><i class="fa fa-pinterest"></i></a></li>
            <li class="gplus animated bounceIn wow delay-05s"><a href="javascript:void(0)"><i class="fa fa-google-plus"></i></a></li>
          </ul>
        </div>
        <div class="col-lg-8 wow fadeInLeft delay-06s">
          <div class="form">
				<!--NOTE: Update your email Id in "contact_me.php" file in order to receive emails from your contact form-->
		<form name="sentMessage" id="contactForm"  novalidate>
		<div class="control-group">
		<div class="controls">
		<input type="text" class="form-control input-text"
		placeholder="Full Name" id="name" required
		data-validation-required-message="Please enter your name" />
		<p class="help-block"></p>
		</div>
		</div>
		<div class="control-group">
		<div class="controls">
		<input type="email" class="form-control input-text" placeholder="Email"
		id="email" required
		data-validation-required-message="Please enter your email" />
		</div>
		</div>

		<div class="control-group">
		<div class="controls">
		<textarea rows="10" cols="100" class="form-control input-text"
		placeholder="Message" id="message" required
		data-validation-required-message="Please enter your message" minlength="5"
		data-validation-minlength-message="Min 5 characters"
		maxlength="999" style="resize:none"></textarea>
		</div>
		</div> 		 
		<div id="success"> </div> <!-- For success/fail messages -->
		<button type="submit" class="btn btn-primary input-btn pull-right">Send</button><br />
		</form>

          </div>
        </div>
      </div>
    </section>
  </div>
  <div class="container">
    <div class="footer_bottom"><span>Copyright &copy; 2018.Su Zhou Green Drive Info Tech Co., Ltd.</span></span></div>
  </div>
</footer>

<script type="text/javascript" src="<%=basePath%>static/custom/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/jquery-scrolltofixed.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/jquery.nav.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/jquery.isotope.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/wow.js"></script>
 <script src="contact/jqBootstrapValidation.js"></script>
 <script src="contact/contact_me.js"></script>
<script type="text/javascript" src="<%=basePath%>static/custom/custom.js"></script>

</body>
</html>
