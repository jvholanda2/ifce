#include "SanUSB1.h"
#include "string.h"
#define BUFFER_SIZE 64
#pragma interrupt interrupcao //Tem que estar aqui ou dentro do firmware.c

void clear_local_buffer(void);

typedef struct {
    char items[BUFFER_SIZE];
    int first;
    int last;
    int count;
} ring_buffer;

//Configurations
char ip_addr[64] = "ypushtcp.cloudapp.net";
int ip_port = 8080;
char device_id[8] = "222";
//flags
char wifi_connected = 0, receive_enabled = 0;
//state
char c, pos=0, blink = 0;
ring_buffer buffer;
char recv[BUFFER_SIZE];

void init_buffer(ring_buffer* buffer) {
    buffer->first = 0;
    buffer->last = 0;
    buffer->count = 0;
}

void push(ring_buffer* buffer, char c) {
    buffer->items[buffer->last] = c;
    buffer->last = (buffer->last + 1) % BUFFER_SIZE;
    buffer->count++;
}

char pop(ring_buffer* buffer) {
    char ret = buffer->items[buffer->first];
    buffer->first = (buffer->first + 1) % BUFFER_SIZE;
    buffer->count--;

    return ret;
}

void interrupcao() {
    if (serial_interrompeu) {
        serial_interrompeu = 0;
        c = le_serial();
        if (receive_enabled) {
            push(&buffer, c);
            //O primeiro caractere legível que o módulo envia é 'r'
        } else if (c == 'r') {
            receive_enabled = 1;
            push(&buffer, c);
        }
    }
}

void main() {
    clock_int_4MHz();
    TRISB = 0;
    memset(recv, 0, BUFFER_SIZE);
    init_buffer(&buffer);
    habilita_interrupcao(recep_serial);
    taxa_serial(9600);
    nivel_alto(pin_b7);
    tempo_ms(500);
    nivel_baixo(pin_b7);
    //printf("AT+CIPSTART=\"TCP\",\"%s\",%d\r\n", ip_addr, ip_port);
    while (1) {
        if (!entrada_pin_e3) {
            Reset();
        }//pressionar o botão para gravação
        while (blink) {
            nivel_baixo(pin_b7);
            tempo_ms(500);
            nivel_alto(pin_b7);
            tempo_ms(100);
            blink--;
        }

        if (buffer.count > 0) {
            c = pop(&buffer);
            recv[pos++] = c;
        }
        if (pos > 0) {
            if (strchr(recv, '>')) {
                tempo_ms(100);
                sendsw(device_id);
                clear_local_buffer();
            }

            if (strstrrampgm(recv, "+IPD,") && strchr(recv, ':')) {
                int size;
                char* str = strchr(recv, ',') + 1;
                str = strtokpgmram(str, ":");
                size = atoi(str);
                //Apaga o buffer local
                clear_local_buffer();
                while (pos < size) {
                    if (buffer.count > 0) {
                        recv[pos++] = pop(&buffer);
                    }
                }
                blink = atoi(recv);
                clear_local_buffer();
            }

            if (strstrrampgm(recv, "\r\n")) {
                if (strstrrampgm(recv, "WIFI GOT IP")) {
                    printf("AT+CIPSTART=\"TCP\",\"%s\",%d\r\n", ip_addr, ip_port);
                    wifi_connected = 1;
                }
                if (strstrrampgm(recv, "CONNECT") && wifi_connected) {
                    printf("AT+CIPSEND=%d\r\n", strlen(device_id));
                }
                if (strstrrampgm(recv, "CLOSED") != NULL) {
                    nivel_baixo(pin_b7);
                }
                memset(recv, '\0', BUFFER_SIZE);
                pos = 0;
            }

        }

    }
}

void clear_local_buffer() {
    memset(recv, '\0', BUFFER_SIZE);
    pos = 0;
}