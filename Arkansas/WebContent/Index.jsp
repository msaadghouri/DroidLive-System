<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="Resources/others/jquery/jquery.min.1.12.4.js"></script>
<!-- <script type="text/javascript" src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script> -->

<script type="text/javascript" src="Resources/others/moment/moment.min.2.18.1.js"></script>
<!-- <script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script> -->

<link rel="stylesheet" type="text/css"
	href="Resources/bootstrap/dist/css/bootstrap.css" />
<!-- <link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap/3/css/bootstrap.css" /> -->
<title>Droid Analyzer</title>
</head>
<body>

	<div id="login-overlay" class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">

				<h4 class="modal-title" id="myModalLabel">Droid Analyzer-Data Acquisition
					and Analysis</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-6">
						<div class="well">
							<form action="LoginServlet" method="post">
								<div class="form-group">
									<label class="control-label">E-mail</label> <input type="text"
										class="form-control" id="email" name="email" placeholder="E-mail">
									<span class="help-block"></span>
								</div>
								<div class="form-group">
									<label class="control-label">Password</label> <input type="password"
										placeholder="Password" class="form-control" id="password" name="password">
								</div>
								<div id="loginErrorMsg">${errorMessage }</div>
								<div class="checkbox">
									<label> <input type="checkbox" name="remember" id="remember">
										Remember login
									</label>

								</div>
								<button type="submit" name="loginButton" id="button1"
									class="btn btn-success btn-block">Login</button>
								<div style="margin-top: 10px;">
									Forgot <a href="">password?</a>
								</div>
							</form>
						</div>
					</div>
					<div class="col-xs-6">
						<p class="lead">
							Register now for <span class="text-success">$$$</span>
						</p>
						<ul class="list-unstyled" style="line-height: 2">
							<li><span class="glyphicon glyphicon-ok"></span> Device Examination</li>
							<li><span class="glyphicon glyphicon-ok"></span> Browser History Analysis</li>
							<li><span class="glyphicon glyphicon-ok"></span> Searched Keywords<small>(support
									for Google)</small></li>
							<li><span class="glyphicon glyphicon-ok"></span> Call Log Anallysis</li>
							<li><span class="glyphicon glyphicon-ok"></span> SMS Analysis</li>
						</ul>
						<p>
							<a href="" class="btn btn-info btn-block"> Register now!</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
