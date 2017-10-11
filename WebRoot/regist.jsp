<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>欢迎注册EasyMall</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${app}/css/regist.css"/>
		
		<!-- 引入jQuery库 -->
		<script type="text/javascript" src="${app}/js/jquery-1.4.2.js"></script>
		
		<script>
			/* 文档就绪事件函数 */
			$(function(){
				/* 点击图片换一张验证码 */
				$("#img").click(function(){
					$(this).attr("src","${app}"
						+"/servlet/ValiImageServlet?time="
						+new Date().getTime());
				});
				
				/* 给所有输入框添加失去输入焦点事件, 当失去输入焦点时检查
				输入框是否为空或者两次密码是否一致或者邮箱格式是否正确 */
				//用户名
				$("input[name='username']").blur(function(){
					if(!formObj.checkNull("username", "用户名不能为空!")){
						return;
					}
					
					//如果用户名不为空, 使用ajax检查用户名是否存在
					var username = $(this).val();
					
					/* 使用GET或POST方法实现ajax */
					$.post("${app}"
						+"/servlet/AjaxCheckUsernameServlet", 
						{"username": username}, function(result){
						//处理响应结果
						$("#username_msg").html(result);
					});
					
					
				});
				//密码
				$("input[name='password']").blur(function(){
					formObj.checkNull("password", "密码不能为空!");
					formObj.checkPassword("password", "两次密码不一致!");
				});
				//确认密码
				$("input[name='password2']").blur(function(){
					formObj.checkNull("password2", "确认密码不能为空!");
					formObj.checkPassword("password", "两次密码不一致!");
				});
				//昵称
				$("input[name='nickname']").blur(function(){
					formObj.checkNull("nickname", "昵称不能为空!");
				});
				//邮箱
				$("input[name='email']").blur(function(){
					formObj.checkNull("email", "邮箱不能为空!");
					formObj.checkEmail("email", "邮箱格式不正确!");
				});
				//验证码
				$("input[name='valistr']").blur(function(){
					formObj.checkNull("valistr", "验证码不能为空!");
				});
				
			})
			
			/* 注册表单的js校验 */
			var formObj = {
				"checkForm" : function(){
					//1.非空校验
					var res1 = this.checkNull("username", "用户名不能为空!");
					var res2 = this.checkNull("password", "密码不能为空!");
					var res3 = this.checkNull("password2", "确认密码不能为空!");
					var res4 = this.checkNull("nickname", "昵称不能为空!");
					var res5 = this.checkNull("email", "邮箱不能为空!");
					var res6 = this.checkNull("valistr", "验证码不能为空!");
					
					//2.两次密码是否一致校验
					var res7 = this.checkPassword("password", "两次密码不一致!");
					var res8 = this.checkEmail("email", "邮箱格式不正确!");
					
					//3.邮箱格式校验
					
					return res1&&res2&&res3&&res4&&res5&&res6&&res7&&res8;
				},
				"checkNull" : function(name, msg){
					var value = $("input[name='"+name+"']").val();
					
					//清空之前的提示消息
					this.setMsg(name, "");
					
					if(value.trim() == ""){
						this.setMsg(name, msg);
						return false;
					}
					return true;
				},
				/* 设置错误提示消息 */
				"setMsg" : function(name, msg){
					//设置提示消息
					var $span = $("input[name='"+name+"']").nextAll("span");
					$span.html(msg);
					$span.css("color", "red");
				},
				/* 检查两次密码是否一致 */
				"checkPassword" : function(name, msg){
					var psw1 = $("input[name='"+name+"']").val();
					var psw2 =  $("input[name='"+name+"2']").val();
					
					if(psw1 != "" && psw2 != ""){
						//清空之前的提示消息
						this.setMsg(name+"2", "");
						if(psw1 != psw2){
							//设置提示消息
							this.setMsg(name+"2", msg);
							return false;
						}
					}
					return true;
				},
				/* 检查邮箱格式是否正确 */
				"checkEmail" : function(name, msg){
					var email = $("input[name='"+name+"']").val();
					var regExp = /^\w+@\w+(\.\w+)+$/;
					
					if(email != ""){
						if(!regExp.test(email)){
							this.setMsg(name, msg);
							return false;
						}
					}
					return true;
				}
				
			}
		</script>
	</head>
	<body>
		<!-- onsubmit事件在表单提交时触发, 该事件会根据返回值决定
			是否提交表单, 如果onsubmit="return true"会继续提交表单
			如果onsubmit="return fasle"表单将不会提交!
			
			onsubmit=""引号中报错并不是因为代码有问题, 而是myeclipse
			工具在检查语法认为这个代码有问题, 其实没有错误!!
		 -->
		
		<form onsubmit="return formObj.checkForm()" action="${app}/servlet/RegistServlet" method="POST">
			<h1>欢迎注册EasyMall</h1>
			<table>
				<tr>
					<td colspan="2" style="text-align:center;color:red">
						${ msg }
					</td>
				</tr>
				<tr>
					<td class="tds">用户名：</td>
					<td>
						<input type="text" name="username" 
							value="${ param.username }"/>
						<span id="username_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">密码：</td>
					<td>
						<input type="password" name="password"
							value="${ param.password }"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">确认密码：</td>
					<td>
						<input type="password" name="password2"
							value="${ param.password2 }"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">昵称：</td>
					<td>
						<input type="text" name="nickname"
							value="${ param.nickname }"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">邮箱：</td>
					<td>
						<input type="text" name="email"
							value="${ param.email }"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">验证码：</td>
					<td>
						<input type="text" name="valistr"
							value="${ param.valistr }"
						/>
						<img id="img" src="${app}/servlet/ValiImageServlet" alt="" />
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="sub_td" colspan="2" class="tds">
						<input type="submit" value="注册用户"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

