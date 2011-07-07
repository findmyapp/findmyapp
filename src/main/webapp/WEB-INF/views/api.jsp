<!DOCTYPE html
  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Services</title><style type="text/css">
                    body {
                        font-family: sans-serif;
                        font-size: 0.85em;
                        margin: 2em 8em;
                    }
                    .methods {
                        background-color: #eef;
                        padding: 1em;
                    }
                    h1 {
                        font-size: 2.5em;
                    }
                    h2 {
                        border-bottom: 1px solid black;
                        margin-top: 1em;
                        margin-bottom: 0.5em;
                        font-size: 2em;
                       }
                    h3 {
                        color: orange;
                        font-size: 1.75em;
                        margin-top: 1.25em;
                        margin-bottom: 0em;
                    }
                    h4 {
                        margin: 0em;
                        padding: 0em;
                        border-bottom: 2px solid white;
                    }
                    h6 {
                        font-size: 1.1em;
                        color: #99a;
                        margin: 0.5em 0em 0.25em 0em;
                    }
                    dd {
                        margin-left: 1em;
                    }
                    tt {
                        font-size: 1.2em;
                    }
                    table {
                        margin-bottom: 0.5em;
                    }
                    th {
                        text-align: left;
                        font-weight: normal;
                        color: black;
                        border-bottom: 1px solid black;
                        padding: 3px 6px;
                    }
                    td {
                        padding: 3px 6px;
                        vertical-align: top;
                        background-color: f6f6ff;
                        font-size: 0.85em;
                    }
                    td p {
                        margin: 0px;
                    }
                    ul {
                        padding-left: 1.75em;
                    }
                    p + ul, p + ol, p + dl {
                        margin-top: 0em;
                    }
                    .optional {
                        font-weight: normal;
                        opacity: 0.75;
                    }
                </style></head>
   <body>
      <h1>Services</h1>
      <ul>
         <li><a href="#resources">Resources</a><ul>
               <li><a href="#http://findmyapp.net/findmyapp#echo">http://findmyapp.net/findmyapp/echo</a></li>
               <li><a href="#http://findmyapp.net/findmyapp#appstore">http://findmyapp.net/findmyapp/appstore/</a><ul>
                     <li><a href="#http://findmyapp.net/findmyapp#list">http://findmyapp.net/findmyapp/appstore//list</a></li>
                  </ul>
               </li>
               <li><a href="#http://findmyapp.net/findmyapp#position">http://findmyapp.net/findmyapp/position/</a><ul>
                     <li><a href="#http://findmyapp.net/findmyapp#sample">http://findmyapp.net/findmyapp/position//sample</a></li>
                     <li><a href="#http://findmyapp.net/findmyapp#user">http://findmyapp.net/findmyapp/position//user/{id}</a></li>
                     <li><a href="#http://findmyapp.net/findmyapp#users">http://findmyapp.net/findmyapp/position//users</a></li>
                  </ul>
               </li>
               <li><a href="#http://findmyapp.net/findmyapp#location">http://findmyapp.net/findmyapp/location</a><ul>
                     <li><a href="#http://findmyapp.net/findmyapp#id">http://findmyapp.net/findmyapp/location/{id}</a><ul>
                           <li><a href="#http://findmyapp.net/findmyapp#fact">http://findmyapp.net/findmyapp/location/{id}/fact</a></li>
                           <li><a href="#http://findmyapp.net/findmyapp#humidity">http://findmyapp.net/findmyapp/location/{id}/humidity</a></li>
                           <li><a href="#http://findmyapp.net/findmyapp#noise">http://findmyapp.net/findmyapp/location/{id}/noise</a></li>
                           <li><a href="#http://findmyapp.net/findmyapp#temperature">http://findmyapp.net/findmyapp/location/{id}/temperature</a></li>
                           <li><a href="#http://findmyapp.net/findmyapp#user">http://findmyapp.net/findmyapp/location/{id}/user</a></li>
                        </ul>
                     </li>
                  </ul>
               </li>
               <li><a href="#http://findmyapp.net/findmyapp#ukaprogram">http://findmyapp.net/findmyapp/program</a><ul>
                     <li><a href="#http://findmyapp.net/findmyapp#ukaYear">http://findmyapp.net/findmyapp/program/{ukaYear}</a><ul>
                           <li><a href="#http://findmyapp.net/findmyapp#places">http://findmyapp.net/findmyapp/program/{ukaYear}/places</a></li>
                        </ul>
                     </li>
                  </ul>
               </li>
            </ul>
         </li>
         <li><a href="#representations">Representations</a><ul>
               <li><a href="#d2e16">text/html<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e18">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e23">text/html<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e25">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e47">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               <li><a href="#d2e68">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e73">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e78">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               <li><a href="#d2e83">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/sample} Response">sam:Response</abbr>)</a></li>
               <li><a href="#d2e125">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               <li><a href="#d2e130">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/user/1} Response">ns:Response</abbr>)</a></li>
               <li><a href="#d2e147">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/users} Response">user:Response</abbr>)</a></li>
               <li><a href="#d2e158">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e163">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e168"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e170"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e172"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e174"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e176"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e179"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e184">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               <li><a href="#d2e211"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">data</abbr>)</a></li>
               <li><a href="#d2e213"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">data</abbr>)</a></li>
               <li><a href="#d2e215"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">HTML</abbr>)</a></li>
               <li><a href="#d2e220">application/json (<abbr title="{http://localhost/findmyapp/location/1/fact} Response">fact:Response</abbr>)</a></li>
               <li><a href="#d2e225">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               <li><a href="#d2e267">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               <li><a href="#d2e294">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               <li><a href="#d2e299">application/json (<abbr title="{http://findmyapp.net/findmyapp/location/1/user} Response">user:Response</abbr>)</a></li>
               <li><a href="#d2e346">application/json (<abbr title="{http://localhost/findmyapp/program/uka11/places} Response">plac:Response</abbr>)</a></li>
               <li><a href="#d2e348">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
            </ul>
         </li>
      </ul>
      <h2 id="resources">Resources</h2>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#echo">echo</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#echo">GET</h4>
               <p>Service for checking availability of the server. Returns a static value.</p>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e16">text/html<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e18">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e23">text/html<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e25">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#appstore">appstore</h3>
         <h6>Methods</h6>
         <div class="methods"></div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#list">list</h3>
         <p>Retreives a list of available applications for the different platforms</p>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#get">GET</h4>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e47">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#position">position</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#get_position">POST</h4>
               <p><em>acceptable request representations:</em></p>
               <ul>
                  <li><a href="#d2e158">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e163">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e168"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e170"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e172"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e174"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e176"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
                  <li><a href="#d2e179"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e184">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#sample">sample</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#insert_sample">POST</h4>
               <p>Method for adding samples of signals to database in order to create data for which to run the Euclidean distance.</p>
               <p><em>acceptable request representations:</em></p>
               <ul>
                  <li><a href="#d2e68">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e73">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e78">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e83">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/sample} Response">sam:Response</abbr>)</a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#user">user</h3>
         <h6>resource-wide template parameters</h6>
         <table>
            <tr>
               <th>parameter</th>
               <th>value</th>
               <th>description</th>
            </tr>
            <tr>
               <td>
                  <p><strong>id</strong></p>
               </td>
               <td>
                  <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em><small> (required)</small></p>
                  <p>Default: <tt></tt></p>
               </td>
               <td>
                  <p>Id of the user to update</p>
               </td>
            </tr>
         </table>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#insert_user_position">POST</h4>
               <h6>request query parameters</h6>
               <table>
                  <tr>
                     <th>parameter</th>
                     <th>value</th>
                     <th>description</th>
                  </tr>
                  <tr>
                     <td>
                        <p><strong>locationId</strong></p>
                     </td>
                     <td>
                        <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em></p>
                        <p>Default: <tt></tt></p>
                     </td>
                     <td>
                        <p>The id of the location where the user is located</p>
                     </td>
                  </tr>
               </table>
            </div>
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#get_user_position">GET</h4>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e125">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e130">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/user/1} Response">ns:Response</abbr>)</a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#users">users</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#get_position_of_all_users">GET</h4>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e147">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/users} Response">user:Response</abbr>)</a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#location">location</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#id">id</h3>
         <h6>resource-wide template parameters</h6>
         <table>
            <tr>
               <th>parameter</th>
               <th>value</th>
               <th>description</th>
            </tr>
            <tr>
               <td>
                  <p><strong>id</strong></p>
               </td>
               <td>
                  <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em><small> (required)</small></p>
                  <p>Default: <tt></tt></p>
               </td>
               <td></td>
            </tr>
         </table>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#fact">fact</h3>
         <h6>resource-wide template parameters</h6>
         <table>
            <tr>
               <th>parameter</th>
               <th>value</th>
               <th>description</th>
            </tr>
            <tr>
               <td>
                  <p><strong>id</strong></p>
               </td>
               <td>
                  <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em><small> (required)</small></p>
                  <p>Default: <tt></tt></p>
               </td>
               <td></td>
            </tr>
         </table>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e211"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">data</abbr>)</a></li>
                  <li><a href="#d2e213"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">data</abbr>)</a></li>
                  <li><a href="#d2e215"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">HTML</abbr>)</a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e220">application/json (<abbr title="{http://localhost/findmyapp/location/1/fact} Response">fact:Response</abbr>)</a></li>
               </ul>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e225">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               </ul>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#humidity">humidity</h3>
         <h6>resource-wide template parameters</h6>
         <table>
            <tr>
               <th>parameter</th>
               <th>value</th>
               <th>description</th>
            </tr>
            <tr>
               <td>
                  <p><strong>id</strong></p>
               </td>
               <td>
                  <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em><small> (required)</small></p>
                  <p>Default: <tt></tt></p>
               </td>
               <td></td>
            </tr>
         </table>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#noise">noise</h3>
         <h6>resource-wide template parameters</h6>
         <table>
            <tr>
               <th>parameter</th>
               <th>value</th>
               <th>description</th>
            </tr>
            <tr>
               <td>
                  <p><strong>id</strong></p>
               </td>
               <td>
                  <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em><small> (required)</small></p>
                  <p>Default: <tt></tt></p>
               </td>
               <td></td>
            </tr>
         </table>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#temperature">temperature</h3>
         <h6>resource-wide template parameters</h6>
         <table>
            <tr>
               <th>parameter</th>
               <th>value</th>
               <th>description</th>
            </tr>
            <tr>
               <td>
                  <p><strong>id</strong></p>
               </td>
               <td>
                  <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em><small> (required)</small></p>
                  <p>Default: <tt></tt></p>
               </td>
               <td></td>
            </tr>
         </table>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e267">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               </ul>
            </div>
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 2">POST</h4>
               <h6>request query parameters</h6>
               <table>
                  <tr>
                     <th>parameter</th>
                     <th>value</th>
                     <th>description</th>
                  </tr>
                  <tr>
                     <td>
                        <p><strong>value</strong></p>
                     </td>
                     <td>
                        <p><em><a href="http://www.w3.org/TR/xmlschema-2/#string">string</a></em></p>
                        <p>Default: <tt></tt></p>
                     </td>
                     <td></td>
                  </tr>
               </table>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#ukaprogram">ukaprogram</h3>
         <h6>Methods</h6>
         <div class="methods"></div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#ukaYear">ukaYear</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
            </div>
         </div>
      </div>
      <div class="resource">
         <h3 id="http://findmyapp.net/findmyapp#places">places</h3>
         <h6>Methods</h6>
         <div class="methods">
            <div class="method">
               <h4 id="http://findmyapp.net/findmyapp#Method 1">GET</h4>
               <p><em>available response representations:</em></p>
               <ul>
                  <li><a href="#d2e346">application/json (<abbr title="{http://localhost/findmyapp/program/uka11/places} Response">plac:Response</abbr>)</a></li>
                  <li><a href="#d2e348">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)</a></li>
               </ul>
            </div>
         </div>
      </div>
      <h2 id="representations">Representations</h2>
      <h3 id="d2e16">text/html<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e18">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e23">text/html<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e25">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e47">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e68">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e73">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e78">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e83">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/sample} Response">sam:Response</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e125">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e130">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/user/1} Response">ns:Response</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e147">application/json (<abbr title="{http://findmyapp.net/findmyapp/position/users} Response">user:Response</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e158">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e163">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e168"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e170"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e172"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e174"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e176"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e179"><abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e184">application/json<abbr title="{http://wadl.dev.java.net/2009/02} "></abbr></h3>
      <h3 id="d2e211"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">data</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e213"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">data</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e215"> (<abbr title="{http://wadl.dev.java.net/2009/02} ">HTML</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e220">application/json (<abbr title="{http://localhost/findmyapp/location/1/fact} Response">fact:Response</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e225">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e267">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e294">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e299">application/json (<abbr title="{http://findmyapp.net/findmyapp/location/1/user} Response">user:Response</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e346">application/json (<abbr title="{http://localhost/findmyapp/program/uka11/places} Response">plac:Response</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
      <h3 id="d2e348">text/html (<abbr title="{http://wadl.dev.java.net/2009/02} ">html</abbr>)
      </h3>
      <div class="representation">
         <h6>XML Schema</h6>
         <p><em>Source: <a href=""></a></em></p><pre></pre></div>
   </body>
</html>