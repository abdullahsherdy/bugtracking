import React, { createContext, useContext, useState, useEffect } from "react";
import { Bug, BugSeverity, BugStatus, Comment, User } from "@/types";
// import { bugs as initialBugs } from "@/data/mockData"; // Comment out static data
import { useToast } from "@/components/ui/use-toast";
import { useAuth } from "@/context/AuthContext";

interface BugContextProps {
  bugs: Bug[];
  addBug: (
    title: string,
    description: string,
    severity: BugSeverity,
    reportedBy: User
  ) => void;
  updateBugStatus: (bugId: string, status: BugStatus) => void;
  assignBug: (bugId: string, assignedTo: User) => void;
  addComment: (bugId: string, text: string, user: User) => void;
  getBugById: (id: string) => Bug | undefined;
}

const BugContext = createContext<BugContextProps | undefined>(undefined);

export const BugProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [bugs, setBugs] = useState<Bug[]>([]);
  const { toast } = useToast();
  const { currentUser } = useAuth();

  useEffect(() => {
    const fetchBugs = async () => {
      try {
        const response = await fetch("http://localhost:8081/api/bugs");
        if (!response.ok) {
          throw new Error("Failed to fetch bugs");
        }
        const data = await response.json();
        setBugs(data);
      } catch (error) {
        console.error("Error fetching bugs:", error);
        toast({
          title: "Error",
          description: "Failed to load bugs from the backend.",
          variant: "destructive",
        });
      }
    };
    fetchBugs();
  }, [toast]);

  const addBug = async (
    title: string,
    description: string,
    severity: BugSeverity,
    reportedBy: User
  ) => {
    try {
      const response = await fetch("http://localhost:8081/api/bugs", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ title, description, severity, reportedBy }),
      });
      if (!response.ok) {
        throw new Error("Failed to add bug");
      }
      const newBug = await response.json();
      setBugs((prevBugs) => [...prevBugs, newBug]);
      toast({
        title: "Bug Created",
        description: "New bug has been successfully created",
      });
    } catch (error) {
      console.error("Error adding bug:", error);
      toast({
        title: "Error",
        description: "Failed to create bug.",
        variant: "destructive",
      });
    }
  };

  const updateBugStatus = async (bugId: string, status: BugStatus) => {
    if (
      status === "closed" &&
      (!currentUser || !["manager", "tester"].includes(currentUser.role))
    ) {
      toast({
        title: "Permission Denied",
        description: "Only managers or testers can close a bug.",
        variant: "destructive",
      });
      return;
    }

    try {
      const response = await fetch(`http://localhost:8081/api/bugs/${bugId}/status`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ status }),
      });
      if (!response.ok) {
        throw new Error("Failed to update bug status");
      }
      const updatedBug = await response.json();
      setBugs((prevBugs) =>
        prevBugs.map((bug) => (bug.id === bugId ? updatedBug : bug))
      );
      toast({
        title: "Status Updated",
        description: `Bug status changed to ${status}`,
      });
    } catch (error) {
      console.error("Error updating bug status:", error);
      toast({
        title: "Error",
        description: "Failed to update bug status.",
        variant: "destructive",
      });
    }
  };

  const assignBug = async (bugId: string, assignedTo: User) => {
    try {
      const response = await fetch(`http://localhost:8081/api/bugs/${bugId}/assign`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ assignedTo }),
      });
      if (!response.ok) {
        throw new Error("Failed to assign bug");
      }
      const updatedBug = await response.json();
      setBugs((prevBugs) =>
        prevBugs.map((bug) => (bug.id === bugId ? updatedBug : bug))
      );
      toast({
        title: "Bug Assigned",
        description: `Bug assigned to ${assignedTo.name}`,
      });
    } catch (error) {
      console.error("Error assigning bug:", error);
      toast({
        title: "Error",
        description: "Failed to assign bug.",
        variant: "destructive",
      });
    }
  };

  const addComment = async (bugId: string, text: string, user: User) => {
    try {
      const response = await fetch(`http://localhost:8081/api/bugs/${bugId}/comments`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ text, userId: user.id }),
      });
      if (!response.ok) {
        throw new Error("Failed to add comment");
      }
      const updatedBug = await response.json();
      setBugs((prevBugs) =>
        prevBugs.map((bug) => (bug.id === bugId ? updatedBug : bug))
      );
      toast({
        title: "Comment Added",
        description: "Your comment has been added to the bug",
      });
    } catch (error) {
      console.error("Error adding comment:", error);
      toast({
        title: "Error",
        description: "Failed to add comment.",
        variant: "destructive",
      });
    }
  };

  const getBugById = (id: string) => {
    return bugs.find((bug) => bug.id === id);
  };

  return (
    <BugContext.Provider
      value={{
        bugs,
        addBug,
        updateBugStatus,
        assignBug,
        addComment,
        getBugById,
      }}
    >
      {children}
    </BugContext.Provider>
  );
};

export const useBug = () => {
  const context = useContext(BugContext);
  if (context === undefined) {
    throw new Error("useBug must be used within a BugProvider");
  }
  return context;
};
