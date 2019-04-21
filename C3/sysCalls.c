/*
Four system calls are provided for creating a
     process, ending a process, and waiting for a process to complete.  These
     system calls are fork(), the "exec" family, wait(), and exit().

     System calls are functions used in the kernel itself.  To the
     programmer, the system call appears as a normal C function call.
     However since a system call executes code in the kernel, there must be a
     mechanism to change the mode of a process from user mode to kernel mode.
	
	The library functions
     typically invoke an instruction that changes the process execution mode
     to kernel mode and causes the kernel to start executing code for system
     calls.  The instruction that causes the mode change is often referred to
     as an "operating system trap" which is a software generated interrupt.
*/
#include<unistd.h>
#include<stdio.h>
#include<stdlib.h>

void processSnapshot(){
	printf("Displaying Process Snapshot");
	system("ps -ef");
	printf("\n\n");
}

void forkProcess(){
	int fork_pid = fork();
	if(fork_pid == 0){
		printf("Child process created successfully\n");
		int proc_id = getpid();
		printf("PID of child : %d\n", proc_id);
		exit(0);
	}
	if(fork_pid < 0){
		printf("Failed to fork child process\n");
	}
	if(fork_pid > 0){
		wait((int *)0);
		int proc_id = getpid();
		printf("PID of parent : %d\n", proc_id);
	}
	
}

void getExecutionParams(char** params){
	
	int i=0;
	printf("Enter the path of executable file: \n");	//filename is executable binary file
	params[0] = (char*)malloc(100);
	scanf("%s", params[0]);
	for(i=1; i<3; i++){
		printf("enter the parameter name, or enter done if done\n");
		params[i] = (char*)malloc(20);
		scanf("%s", params[i]);
		if(!strcmp(params[i],"done")){
			params[i] = (char *)0;
			return;
		}
	}
}

void forkProcessAndExecute(){
	printf("Reading Parameters...\n");
	char* params[20];
	getExecutionParams(params);
	printf("Forking....\n");
	int process = fork();

	if(process>0){
			
		wait((int*)0);
		printf("Resuming current process...\n");
	}
	if(process == 0){
		printf("Starting requested process... \n");
		execv(params[0], params);		//for execv, file name must be fully qualified path
		printf("Process executed successfully!\n");
	}	
}


void displayMenu(){
	printf("Enter a choice:\n\t1.View the process snapshot\n\t2.Fork the current process\n\t3.Execute a process\n\t4.Exit\n");
}

int main(){
	int choice= 0;
	do{
		displayMenu();
		scanf("%d", &choice);
		switch(choice){
			case 1:
				processSnapshot();
				break;
				
			case 2:
				forkProcess();
				break;
				
			case 3:
				forkProcessAndExecute();
				break;
				
			case 4:
				break;
				
			default:
				break;
				
		}
	}while(choice!=4);
	
	return 0;
}
