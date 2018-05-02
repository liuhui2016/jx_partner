/*
 * jQuery Combobox
 * http://jqueryui.com/demos/autocomplete/#combobox
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 */

/* FIX 下拉超长则纵向滚动条*/

/* jQueryUI默认大按钮样式
<style>
button.comboboxButton { margin-left: -1px; }
.ui-autocomplete-input {margin: 0; padding: 0.48em 0em 0.47em 0.4em; *padding:0.68em 0 0.68em 0.4em;}
ul.ui-autocomplete {max-height: 280px; overflow-x: hidden; overflow-y: auto; padding:2px;}
</style>
*/

/* jQueryUI小按钮样式
<style>
button.comboboxButton {width:2em;  margin-left: -1px;}
button.comboboxButton .ui-button-text {display:block; line-height:1;}
.ui-autocomplete-input {margin:0; padding:0.3em 0 0.31em 0.3em; *padding:0.38em 0 0.46em 0.3em;}
</style>
*/

// combobox
if (typeof $.widget === 'function') {
(function( $ ) {
		$.widget( "ui.combobox", {
			options: {
				delay: 100,
				size: 0
			},
			_create: function() {
				var self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					size = self.options.size || '',
					width=self.options.width || '',
					//value = selected.val() ? selected.text() : "",
					delay = self.options.delay,
					title = self.options.title;
				// add waiting
				var select_name = select[0] && select[0].selectedIndex >=0 && select[0].options[select[0].selectedIndex].text ||'&nbsp;';
				//var str = select_name ? '<input size="' + size + '" value="' + select_name + '" id="combobox_'+title+'" />' : '<input>';
				var id = title==null? title: title.substring(title.lastIndexOf(".")+1,title.length);
				var str = select_name ? '<input style="width: ' + width + 'px;" size="' + size + '" value="' + select_name + '" id="'+id+'" name="'+title+'" />' : '<input>';
				var input = this.input = $( str )
					.insertAfter( select )
					////////////////////////////.val( value )
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
							var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
							response( select.children( "option" ).map(function() {
								var text = $( this ).text();
								if ( this.value && ( !request.term || matcher.test(text) ) )
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: $.trim(text),
										option: this
									};
							}) );
						},
						select: function( event, ui ) {
							select.val( ui.item.option.value );	// 更新select值
							ui.item.option.selected = true;
							self._trigger( "selected", event, {
								item: ui.item.option
							});
							select.change();
						},
						change: function( event, ui ) {							
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
										this.selected = valid = true;
										return false;
									}
								});
								if ( !valid ) {
									// remove invalid value, as it didn't match anything
									$( this ).val( "" );
									select.val( "" );
									input.data( "autocomplete" ).term = "";
									return false;
								}
						},
						delay: delay
					})
					.addClass( "ui-widget ui-widget-content ui-corner-left" );
				
				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					//$("ul.ui-autocomplete").css("width","200px");
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
				};
				this.img = $( "<img/>" )
					.attr( "tabIndex", -1 )
					.attr( "title", '单击展开' )
					.attr( "src", ctx+"/images/multiselect.gif")
					.addClass("select-combobox")
					.insertAfter( input )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}
						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
				
				this.img.hover(function(){
					$(this).attr( "src", ctx+"/images/multiselect-hover.gif");
				},function(){
					$(this).attr( "src", ctx+"/images/multiselect.gif");
				})
				
			}
			/*
			destroy: function() {
				this.input.remove();
				this.button.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
			*/
		});
	})( jQuery );
}