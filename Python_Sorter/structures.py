""" Sort Demonstrator/Structures
    Author: Victoria Jurkfitz Kessler Thibes
    Date: Dec. 02, 2016
    Course: CST8333 - Programming Language Research Project
"""

from copy import deepcopy

class LinkedList:
    """ Implements a linked list with one node and a link to another """

    node_data = ''
    # Data for this node

    next_node = ''
    # Next node in the list

    def __init__(self, data):
        """ Constructor """
        if len(data) > 0:
            data_copy = deepcopy(data)

            # Takes first item from array
            self.node_data = data_copy.pop(0)

            # Next node is constructed until original array is empty
            if len(data_copy) > 0:
                self.next_node = LinkedList(data_copy)

    def get_string(self):
        """" Used to print results """

        if self.next_node != '':
            return str(self.node_data) + " | Next node: " + str(self.next_node.node_data) + "\n" + self.next_node.get_string()

        else:
            return str(self.node_data) + " | Next node: empty"

    def insert(self,data):

        if self.next_node == '':
            self.next_node = LinkedList(data)

        else:
            self.next_node.insert(data)


class BinaryTree:
    """ Implements a binary tree """

    parent = ''
    # Parent node

    node_data = ''
    # Current node's data

    left = ''
    # Children to the left

    right = ''
    # Children to the right

    def __init__(self,data,parent):
        """ Constructor """

        self.parent = parent

        if len(data) > 0:
            # Takes the middle item from the array
            mid = data.pop(int(len(data)/2))
            self.node_data = mid

            if len(data) > 0:
                # Next half of the array is linked to the right
                self.right = BinaryTree(data[int(len(data)/2):],self)
                if self.right.node_data == '':
                    self.right = ''

            if len(data) > 0:
                # Previous half of the array is linked to the left
                self.left = BinaryTree(data[:int(len(data)/2)],self)
                if self.left.node_data == '':
                    self.left = ''

    def get_string(self):
        """ Called to show results """

        if self.right != '' and self.right.node_data == '':
            self.right = ''

        if self.left != '' and self.left.node_data == '':
            self.left = ''

        return_string = str(self.node_data) + "\tParent: "

        if self.parent != '':
            return_string += str(self.parent.node_data)

        else:
            return_string += " None"

        return_string += "\tChildren: "

        if self.right != '' and self.left != '':
            children_string = str(self.left.node_data) + "," + str(self.right.node_data) + "\t\n"

            return return_string + children_string + self.left.get_string() + "\n" + self.right.get_string()

        elif self.right != '':
            children_string = str(self.right.node_data) + "\t\n"

            return return_string + children_string + self.right.get_string()

        elif self.left != '':
            children_string = str(self.left.node_data) + "\t\n"

            return return_string + children_string + self.left.get_string()

        else:
            return return_string + "None"


class Stack:
    """" Implements a stack """

    node_data = ''
    # Current node's data

    next_node = ''
    # Next item on the stack

    def __init__(self,data):
        """ Constructor """
        if len(data) > 0:
            self.node_data = data.pop()

            if len(data) > 0:
                self.next_node = Stack(data)

    def pop(self):
        """" Takes the first node out of the stack """
        popped = self.node_data

        if self.next_node != '':
            self.node_data = self.next_node.node_data
            self.next_node = self.next_node.next_node

        else:
            self.node_data = ''

        return popped

    def push(self,data):
        """" Puts a new node on top of the stack """
        if self.next_node != '':
            self.next_node.push(self.node_data)
            self.node_data = data

        else:
            self.next_node = Stack([self.node_data])
            self.node_data = data

    def get_string(self):
        """" Called to print results """
        if self.next_node != '' and self.next_node.node_data == '':
            self.next_node = ''

        if self.next_node != '':
            return "\t" + str(self.node_data) + "\n" + self.next_node.get_string()

        else:
            # Last node is at the bottom
            return "Bottom: " + str(self.node_data)

