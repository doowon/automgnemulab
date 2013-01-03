import socket
import simplejson
import subprocess

def main():

	phyNode = []
	phyNicMap = {}

	exe = '/usr/testbed/libexec/ptopgen'
	print exe
	p = subprocess.Popen(exe, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
	lines = p.stdout.readlines()
	retval = p.wait()
	print lines
	
	for line in lines:
		print line
		tokens = line.split()
		if tokens[0] == 'node':
			if tokens[1].startswith('pc'):
				phyNode.append(tokens[1])
				phyNicMap[tokens[1]] = 0
		elif tokens[0] == 'link':
			if tokens[1].startswith('link-pc'):
				for node in phyNode:
					if tokens[1].find(node) != -1:
						phyNicMap[node] = phyNicMap[node] + 1
	json = simplejson.dumps(phyNicMap) 
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	address = '127.0.0.1'
	port = 6000
	s.connect((address, port))
	s.sendall(json)

if __name__ == "__main__":
	main()
