var index = (function (){
        function change(entrada) {
            const body = $("tbody");
            if (body !== null) {
                body.remove();
            }
            if (entrada != null && entrada != "") {
                agregarText(entrada, getText);
            }
        }

        function agregarText(entrada, callback) {
            const promise = new Promise((resolve, reject) => {
                $.ajax({
                    url: "/amazondocker/entra/" + entrada
                }).done(function (response) {
                    resolve(response);
                }).fail(function (msg) {
                    reject(msg);
                });
            });

            promise.then(res => {
                callback(res);
            });
        }

        function getText(data){
            console.log(data);
            console.log(data[0]);
            const tabla = $("table");
            const body = $("tbody");
            if (body != null) {
                body.remove();
            }
            tabla.append("<tbody>");
            const tblBody = $("tbody");
            var columnas="";
            for(const property in data){
                columnas=columnas+'<tr>'
                columnas=columnas+"<td>"+data[property].text+"</td>";
                console.log(data[property].text);
                columnas=columnas+"<td>"+data[property].date+"</td>";
                columnas=columnas+"</tr>";
            }
            tblBody.append(columnas);
            tabla.append("</tbody>");
        }

        return {
            change: change,
            agregarText: agregarText,
            getText: getText
        }

    }
)()