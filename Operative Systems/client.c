#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>

#define PUERTO 9999

int main(int argc, char *argv[]){
    char buffer[200];
    struct sockaddr_in s_addr;
    struct hostent *server;
    
        //abrimos el socket
        int socketfd = socket(AF_INET, SOCK_STREAM, 0);

        if(socketfd >= 0){
            //inicializamos a 0
            bzero((char*) &s_addr, sizeof(s_addr));

            s_addr.sin_port = htons(PUERTO);
            s_addr.sin_family = AF_INET;
            s_addr.sin_addr.s_addr = inet_addr(argv[1]);

            //petición de conexión
            if(connect(socketfd, (struct sockaddr *) &s_addr, sizeof(s_addr)) < 0){
                perror("Error al conectar al socket");
            }else{
                //escribimos en el socket
                write(socketfd,argv[2],sizeof(argv[2]));

                //leemos el resultado que nos pasa el servidor
                read(socketfd, buffer, sizeof(buffer));
                printf("%s", buffer); //imprimimos por pantalla el resultado
                //cerramos el socket
                close(socketfd);
            }

        }else{
            perror("Error al crear el socket");
        }

    return 0;
}