#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>

#define PUERTO 9999

int main(){

    FILE *file;
    char resultado[250];
    char buffer[200];
    struct sockaddr_in s_addr, c_addr;
    socklen_t size_a;
    pid_t pid;

    //abrimos el socket

    int socketfd = socket(AF_INET, SOCK_STREAM, 0);

    if(socketfd == -1){
        perror("No se pudo abrir el socket \n");
        exit(-1);
    }else{
        bzero((char *) &s_addr, sizeof(s_addr));    //inicializamos a 0
        s_addr.sin_family = AF_INET;
        s_addr.sin_port = htons(PUERTO);
        s_addr.sin_addr.s_addr = INADDR_ANY;

        //comprobamos que la dirección es correcta
        if(bind(socketfd, (struct sockaddr *) &s_addr, sizeof(s_addr)) < 0){
            perror("Error \n");
        }else{
            listen(socketfd, 2);    //cola de 2 peticiones

            while(1){

                printf("Aceptando peticion \n");
                pid = fork();   //creamos al proceso hijo

                if(pid == 0){
                    //CÓDIGO DEL PROCESO HIJO
                    size_a = sizeof(c_addr);

                    //aceptamos la petición del cliente
                    int socketfd2 = accept(socketfd, (struct sockaddr *) &c_addr, &size_a);

                    if(socketfd2 < 0){

                        perror("Error aceptando conexion");

                    }else{

                        bzero(buffer, sizeof(buffer)); //inicializamos a 0
                        read(socketfd2, buffer, sizeof(buffer)); //leemos del socket
                
                        file = popen(buffer,"r"); //guardamos el resultado del comando
                        
                        //escribimos en el socket el resultado del comando
                        while(fgets(resultado,sizeof(resultado),file) != NULL){
                            write(socketfd2, resultado, sizeof(resultado));
                        }
                        //cerramos todo
                        pclose(file);
                        close(socketfd2);
                    }
                }else{
                    //CÓDIGO DEL PROCESO PADRE
                    wait(NULL);
                }
            }
        }
    }
    return 0;
}
